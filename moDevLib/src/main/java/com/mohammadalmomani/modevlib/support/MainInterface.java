package com.mohammadalmomani.modevlib.support;



public interface MainInterface {


    default void onItemClick(){}

    default void onItemClick(Object object, int position){};

    default void onDialogDismiss(Object object ){}
}
