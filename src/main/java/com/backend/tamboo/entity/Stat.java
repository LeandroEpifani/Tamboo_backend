package com.backend.tamboo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stat")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 2, scale = 0)
    private BigDecimal percent;

    @Column(nullable = true, length = 1000)
    private String data;

    @OneToOne(mappedBy = "stat")
    private UserTraining userTraining;

    public Stat() {
    }

}
