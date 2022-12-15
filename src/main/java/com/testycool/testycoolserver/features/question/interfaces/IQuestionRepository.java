package com.testycool.testycoolserver.features.question.interfaces;

import com.testycool.testycoolserver.features.question.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IQuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
}
