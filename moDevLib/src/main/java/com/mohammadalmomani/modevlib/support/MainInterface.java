package com.mohammadalmomani.modevlib.support;



public interface MainInterface {


    default void onItemClick(){}

    default void onItemClick(Object object, int position){};

    @Deprecated
    default void onDialogDismiss(Object object ){}

    interface DialogPicker{
        void onDialogDismiss(Object object );
    }
}
