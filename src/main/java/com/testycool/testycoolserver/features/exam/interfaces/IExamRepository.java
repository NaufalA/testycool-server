package com.testycool.testycoolserver.features.exam.interfaces;

import com.testycool.testycoolserver.features.exam.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IExamRepository extends JpaRepository<Exam, Long>, JpaSpecificationExecutor<Exam> {
    Optional<Exam> findByTitle(String title);
    Optional<Exam> findByPassword(String password);
}
