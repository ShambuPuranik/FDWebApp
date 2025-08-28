package com.finace.CustomerService.mqtt;

import com.finace.CustomerService.domain.Customer;
import com.finace.CustomerService.repository.CustomerRepo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MqttUserSubscriber {

    private static final String BROKER = "ssl://broker.hivemq.com:8883";
    private static final String TOPIC = "finace/user/registered";

    private final CustomerRepo customerRepository;
    private IMqttClient client;
    private ExecutorService executorService; // Thread pool

    public MqttUserSubscriber(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void subscribe() {
        try {
            String clientId = "customer-service-" + System.currentTimeMillis();
            client = new MqttClient(BROKER, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);

            client.connect(options);
            System.out.println(" Connected to MQTT broker: " + BROKER);

            // Create thread pool for async processing
            executorService = Executors.newFixedThreadPool(5);

            client.subscribe(TOPIC, 1, (topic, msg) -> {
                executorService.submit(() -> handleMessage(msg));
            });

        } catch (MqttException e) {
            System.err.println(" MQTT subscription failed");
            e.printStackTrace();
        }
    }

    private void handleMessage(MqttMessage msg) {
        String payload = new String(msg.getPayload());
        System.out.println(" Received from MQTT: " + payload);

        try {
            String[] parts = payload.split("\\|");
            Long userId = Long.parseLong(parts[0]);
            String username = parts[1];
            String role = parts[2];

            Customer customer = new Customer();
            customer.setUserId(userId);
            customer.setFirstName(username);
            // Optionally store role in Customer entity

            customerRepository.save(customer);
            System.out.println(" Saved Customer: " + username + " with userId=" + userId);

        } catch (Exception e) {
            System.err.println(" Failed to process MQTT message: " + e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("🔌 Disconnected from MQTT broker");
            }
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
