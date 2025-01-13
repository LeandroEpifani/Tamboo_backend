package com.backend.tamboo.entity;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usertraining")
public class UserTraining {

    @EmbeddedId
    private UserTrainingId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("trainingId")
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private Timestamp assignment;

    @OneToOne
    @JoinColumn(name = "stat_id", unique = true, nullable = false)
    private Stat stat;

    public UserTraining() {
    }

}
