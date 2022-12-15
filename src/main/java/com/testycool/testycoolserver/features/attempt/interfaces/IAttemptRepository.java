package com.testycool.testycoolserver.features.attempt.interfaces;

import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAttemptRepository extends JpaRepository<Attempt, Long>, JpaSpecificationExecutor<Attempt> {
}
