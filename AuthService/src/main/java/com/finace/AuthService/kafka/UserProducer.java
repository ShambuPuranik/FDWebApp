//package com.finace.AuthService.kafka;
//
//import com.finace.AuthService.dto.UserDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserProducer {
//
//    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
//
//    public void sendUserRegisteredEvent(UserDTO userDTO) {
//        kafkaTemplate.send("user_registered_topic", userDTO);
//        System.out.println("User Registered Event sent to Kafka: " + userDTO.getUsername());
//    }
//}
