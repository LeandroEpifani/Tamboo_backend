package com.backend.tamboo.entity;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usertraining")
public class UserTraining {

    @EmbeddedId
    private UserTrainingId id;  // (user_id, training_id)

    // Collego le due parti della PK alle rispettive entità
    @ManyToOne
    @MapsId("userId")        // maps la property userId di UserTrainingId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("trainingId")    // maps la property trainingId di UserTrainingId
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    // assignment (timestamp)
    @Column(nullable = false)
    private Timestamp assignment;

    // Collegamento a stat (FK stat_id)
    @OneToOne
    @JoinColumn(name = "stat_id", unique = true, nullable = false)
    private Stat stat;

    public UserTraining() {
    }

    // Getter e Setter
    // ...
}
