package com.testycool.testycoolserver.features.answer.dtos;

public class AnswerSummary {
    private Integer corrects;
    private Integer wrongs;
    private Integer total;

    public Integer getCorrects() {
        return corrects;
    }

    public void setCorrects(Integer corrects) {
        this.corrects = corrects;
    }

    public Integer getWrongs() {
        return wrongs;
    }

    public void setWrongs(Integer wrongs) {
        this.wrongs = wrongs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
