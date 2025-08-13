package com.finace.CustomerService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {


    private Long customerId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String panCardNum;
    private String occupation;
    private  String address;



}
