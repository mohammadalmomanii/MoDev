package com.mohammadalmomani.modev.support;


public enum StaticString {
  a;

    private int  id;
    private String text;

    StaticString(int id, String text) {
        this.id = id;
        this.text = text;

    }

    StaticString() {
    }
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }


}
