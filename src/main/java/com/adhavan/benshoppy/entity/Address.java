package com.adhavan.benshoppy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private String address;
    private Integer pinCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
