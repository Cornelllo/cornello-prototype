package com.cornello.prototype.model;

import javax.validation.constraints.NotNull;

public class RequestBodyTest {
    private int numeric;
    @NotNull
    private String text;
    
    public RequestBodyTest() {
    }

    public RequestBodyTest(int numeric, String text) {
        this.numeric = numeric;
        this.text = text;
    }

    public int getNumeric() {
        return numeric;
    }

    public void setNumeric(int numeric) {
        this.numeric = numeric;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RequestBodyTest [numeric=" + numeric + ", text=" + text + "]";
    }

}
