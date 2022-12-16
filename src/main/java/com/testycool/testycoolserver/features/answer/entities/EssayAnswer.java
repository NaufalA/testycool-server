package com.testycool.testycoolserver.features.answer.entities;

import com.testycool.testycoolserver.shared.constants.TextFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class EssayAnswer implements Serializable {
    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "text_format")
    private TextFormat textFormat;

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
}