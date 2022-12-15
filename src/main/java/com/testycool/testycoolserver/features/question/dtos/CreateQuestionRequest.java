package com.testycool.testycoolserver.features.question.dtos;

import com.testycool.testycoolserver.features.question.entities.Question;
import com.testycool.testycoolserver.shared.constants.TextFormat;

public class CreateQuestionRequest {
    private Long examId;
    private String content;
    private TextFormat textFormat;
    private Question.Type type;

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
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

    public Question.Type getType() {
        return type;
    }

    public void setType(Question.Type type) {
        this.type = type;
    }
}
