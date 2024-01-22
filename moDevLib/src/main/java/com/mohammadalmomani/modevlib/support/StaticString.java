package com.mohammadalmomani.modevlib.support;


public enum StaticString {
  SMALL(1,""),
  BIG(2,"");

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
