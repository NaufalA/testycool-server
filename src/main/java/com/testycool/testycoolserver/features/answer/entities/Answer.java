package com.testycool.testycoolserver.features.answer.entities;

import com.testycool.testycoolserver.features.attempt.entities.Attempt;
import com.testycool.testycoolserver.features.choice.entities.Choice;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer extends SerialBaseEntity {
    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

    @Embedded
    private EssayAnswer essayAnswer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private Attempt attempt;

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public EssayAnswer getEssayAnswer() {
        return essayAnswer;
    }

    public void setEssayAnswer(EssayAnswer essayAnswer) {
        this.essayAnswer = essayAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }
}