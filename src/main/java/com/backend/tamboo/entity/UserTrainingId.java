package com.backend.tamboo.entity;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserTrainingId implements Serializable {

    private Integer userId;
    private Integer trainingId;

    public UserTrainingId() {
    }

    public UserTrainingId(Integer userId, Integer trainingId) {
        this.userId = userId;
        this.trainingId = trainingId;
    }

    // getter e setter
    // equals & hashCode
    // ...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTrainingId)) return false;
        UserTrainingId that = (UserTrainingId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(trainingId, that.trainingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, trainingId);
    }
}
