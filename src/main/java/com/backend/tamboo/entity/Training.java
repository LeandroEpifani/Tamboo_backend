package com.backend.tamboo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String timeSignature;

    @Column(nullable = false)
    private String bpm;

    @Column(nullable = false, length = 5000)
    private String beat;

    @Column(nullable = false)
    private String name; // Nuovo campo 'name'

    // Relazione con la tabella di join usertraining
    @OneToMany(mappedBy = "training")
    private Set<UserTraining> userTrainings;

    public Training() {
    }


    public Integer getId() {
        return id;
    }
    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getTimeSignature() {
        return timeSignature;
    }
    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public String getBeat() {
        return beat;
    }

    public void setUserTrainings(Set<UserTraining> userTrainings) {
        this.userTrainings = userTrainings;
    }

    public Set<UserTraining> getUserTrainings() {
        return userTrainings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
