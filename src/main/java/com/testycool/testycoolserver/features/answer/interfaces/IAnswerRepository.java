package com.testycool.testycoolserver.features.answer.interfaces;

import com.testycool.testycoolserver.features.answer.dtos.projection.AnswerSummaryQuery;
import com.testycool.testycoolserver.features.answer.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IAnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {
    @Query(
            value =
                    "SELECT " +
                            "coalesce (( " +
                            "select COUNT(c.id) as corrects " +
                            "FROM answers a " +
                            "JOIN questions q ON a.question_id = q.id " +
                            "JOIN choices c ON a.choice_id=c.id " +
                            "where q.id = 1 " +
                            "and c.is_correct = true " +
                            "group by c.id " +
                            "), 0) as corrects, " +
                            "coalesce (( " +
                            "select COUNT(c.id) as wrongs " +
                            "FROM answers a " +
                            "JOIN choices c ON a.choice_id=c.id " +
                            "JOIN questions q ON a.question_id = q.id " +
                            "where q.id = 1 " +
                            "and c.is_correct = false " +
                            "group by c.id " +
                            "), 0) as wrongs",
            nativeQuery = true
    )
    public AnswerSummaryQuery getAnswerSummary(Long questionId);
}
