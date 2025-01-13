package com.backend.tamboo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String role;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}