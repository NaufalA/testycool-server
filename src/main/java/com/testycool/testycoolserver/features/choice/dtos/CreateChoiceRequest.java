package com.testycool.testycoolserver.features.choice.dtos;

import com.testycool.testycoolserver.shared.constants.TextFormat;

public class CreateChoiceRequest {
    private Long questionId;
    private String content;
    private TextFormat textFormat;
    private Boolean isCorrect;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
