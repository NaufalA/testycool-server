package com.testycool.testycoolserver.features.answer.interfaces;

import com.testycool.testycoolserver.features.answer.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {
}
