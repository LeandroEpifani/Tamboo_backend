package com.backend.tamboo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stat")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // percent decimal(2,0)
    @Column(nullable = false, precision = 2, scale = 0)
    private BigDecimal percent;

    // Se non c'è un obbligo di non null in DB, togli nullable = false
    @Column(nullable = true, length = 1000)
    private String data;

    // Relazione OneToOne con usertraining
    // mappedBy = "stat" -> definita in usertraining (joinColumn)
    @OneToOne(mappedBy = "stat")
    private UserTraining userTraining;

    public Stat() {
    }

    // Getter e Setter
    // ...
}
