package com.testycool.testycoolserver.features.answer.dtos;

import com.testycool.testycoolserver.features.answer.entities.EssayAnswer;

public class CreateAnswerRequest {
    private Long participantId;
    private Long questionId;
    private Long choiceId;
    private EssayAnswer essay;

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public EssayAnswer getEssay() {
        return essay;
    }

    public void setEssay(EssayAnswer essay) {
        this.essay = essay;
    }
}
