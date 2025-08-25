package com.finace.AuthService.service;

import com.finace.AuthService.constants.Role;
import com.finace.AuthService.domain.User;
import com.finace.AuthService.dto.AuthResponse;
import com.finace.AuthService.dto.UserDTO;
//import com.finace.AuthService.kafka.UserProducer;
import com.finace.AuthService.mapper.UserMapper;
import com.finace.AuthService.mqtt.MqttUserPublisher;
import com.finace.AuthService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor


public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
//    private final UserProducer userProducer;
    private final MqttUserPublisher mqttUserPublisher;

    public AuthResponse register(UserDTO request) throws Exception {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();
        userRepository.save(user);
       UserDTO savedUserDto = userMapper.toDto(userRepository.save(user));


//        userProducer.sendUserRegisteredEvent(request);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole().name());


        mqttUserPublisher.publishUser(userDTO);



        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(UserDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
