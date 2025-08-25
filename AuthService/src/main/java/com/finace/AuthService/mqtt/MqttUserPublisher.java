package com.finace.AuthService.mqtt;

import com.finace.AuthService.dto.UserDTO;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttUserPublisher {

    private MqttClient mqttClient;

    public MqttUserPublisher() {
        try {

            String broker = "ssl://broker.hivemq.com:8883";
            String clientId = "auth-service-" + System.currentTimeMillis();

            mqttClient = new MqttClient(broker, clientId);
            mqttClient.connect();
            System.out.println(" Connected to MQTT broker: " + broker);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishUser(UserDTO user) {
        try {
            String payload = user.getId() + "|" + user.getUsername() + "|" + user.getRole();
            MqttMessage message = new MqttMessage(payload.getBytes());
            mqttClient.publish("finace/user/registered", message);
            System.out.println(" Published user to MQTT: " + payload);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}


