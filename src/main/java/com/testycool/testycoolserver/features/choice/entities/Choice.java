package com.testycool.testycoolserver.features.choice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.constants.TextFormat;
import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "choices")
public class Choice extends SerialBaseEntity {
    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect = false;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "text_format", nullable = false)
    private TextFormat textFormat;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextFormat getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(TextFormat textFormat) {
        this.textFormat = textFormat;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}