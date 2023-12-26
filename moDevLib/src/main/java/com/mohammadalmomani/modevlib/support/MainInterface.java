package com.mohammadalmomani.modevlib.support;



public interface MainInterface {

    default void onItemClick(){}

    void onItemClick(Object data, int position);
}
