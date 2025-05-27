package com.mohammadalmomani.modevlib;

import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Contains custom binding adapters used for data binding.
 */
public class BindingAdapters {

    /**
     * Makes a view movable by dragging it on the screen when the 'movable' attribute is set to true in XML.
     *
     * @param view     The target view to be made movable.
     * @param movable  Boolean flag to enable or disable movement.
     */
    @BindingAdapter("movable")
    public static void setMovable(View view, Boolean movable) {
        if (movable == null || !movable) return;

        final float[] dX = new float[1];
        final float[] dY = new float[1];
        final long[] startTime = new long[1];
        final int CLICK_THRESHOLD = 200;

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX[0] = view.getX() - event.getRawX();
                        dY[0] = view.getY() - event.getRawY();
                        startTime[0] = System.currentTimeMillis();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX[0])
                                .y(event.getRawY() + dY[0])
                                .setDuration(0)
                                .start();
                        return true;

                    case MotionEvent.ACTION_UP:
                        long clickDuration = System.currentTimeMillis() - startTime[0];
                        if (clickDuration < CLICK_THRESHOLD)  view.callOnClick();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
