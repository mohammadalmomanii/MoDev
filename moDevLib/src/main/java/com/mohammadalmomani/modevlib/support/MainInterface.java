package com.mohammadalmomani.modevlib.support;



public interface MainInterface {

    default void onItemClick(){}

    default void onItemClick(Object data, int position){};
}
