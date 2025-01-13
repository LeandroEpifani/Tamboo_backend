package com.backend.tamboo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45)
    private String surname;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 1000)
    private String description;

    // Relazione Many-to-One con Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {
    }

    public User(String name, String surname, LocalDate date, String gender, String email, String password, String description, Role role) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.description = description;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // La password è nascosta nelle risposte JSON
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @PrePersist
    public void setDefaultRole() {
        if (this.role == null) {
            this.role = new Role();
            this.role.setId(1);
        }
    }
}
