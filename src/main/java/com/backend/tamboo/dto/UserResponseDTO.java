package com.backend.tamboo.dto;

import java.time.LocalDate;

public class UserResponseDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String gender;
    private LocalDate birthDay;
    private String description;
    private Integer role;

    public UserResponseDTO(Integer id, String name, String surname, LocalDate birthDay, String gender, String email, String description, Integer role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.birthDay = birthDay;
        this.description = description;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
    public LocalDate getBirthDay() {
        return birthDay;
    }
}
