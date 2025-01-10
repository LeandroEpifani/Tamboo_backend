package com.backend.tamboo.repository;

import com.backend.tamboo.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Integer> {
}
