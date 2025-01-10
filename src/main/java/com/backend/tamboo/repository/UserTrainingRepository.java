package com.backend.tamboo.repository;

import com.backend.tamboo.entity.UserTraining;
import com.backend.tamboo.entity.UserTrainingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrainingRepository extends JpaRepository<UserTraining, UserTrainingId> {
}
