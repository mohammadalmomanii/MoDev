package com.mohammadalmomani.modevlib.support;



public interface MainInterface {


    default void onItemClick(){}

    default void onItemClick(Object object, int position){};



    interface DialogListener{
         void onItemClick();
    }

    interface DialogPicker{
        void onDialogDismiss(Object object );
    }
}
