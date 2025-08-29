package com.finace.TransactionService.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="fromAcc")
    private String fromAcc;

    @Column(name="toAcc")
    private String toAcc;

    @Column(name="amount")
    private double amount;
}
