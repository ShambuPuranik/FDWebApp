//package com.finace.CustomerService.kafka;
//
////import com.finace.CustomerService.dto.UserDTO;
//import com.finace.AuthService.dto.UserDTO;
//import com.finace.CustomerService.domain.Customer;
//import com.finace.CustomerService.repository.CustomerRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserConsumer {
//
//    private final CustomerRepo customerRepository;
//
//    @KafkaListener(topics = "user_registered_topic", groupId = "customer-service-group")
//    public void consumeUserRegistered(UserDTO userDTO) {
//        System.out.println("📩 Received new user from Kafka: " + userDTO.getUsername());
//        if (!customerRepository.existsByUsername(userDTO.getFirstName())) {
//            Customer customer = Customer.builder()
//                    .firstName(userDTO.getUsername())
////                    .role(userDTO.getRole())
//                    .build();
//
//            customerRepository.save(customer);
//            System.out.println("Saved new Customer: " + customer.getFirstName());
//        } else {
//            System.out.println("Customer already exists: " + userDTO.getFirstName());
//        }
//    }
//
//}
