package com.mohammadalmomani.modevlib.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mohammadalmomani.modevlib.R;

public class FloatingView extends FrameLayout implements View.OnTouchListener {

    /**
     * FloatingView is a custom FrameLayout that provides a draggable floating widget on the screen.
     * It supports smooth dragging within screen bounds and optional snapping to screen edges.
     * <p>
     * ## Features:
     * - Drag and move the view anywhere on the screen horizontally and vertically.
     * - Automatically snaps to the nearest screen edge when released (optional).
     * - Supports click detection distinguishing from drag.
     * - Configurable via XML attribute to enable or disable snap-to-side behavior.
     * <p>
     * ## How to Use:
     * <p>
     * ### 1. Add to XML Layout:
     * ```xml
     * <com.mohammadalmomani.modevlib.support.FloatingView
     *     android:id="@+id/floatingView"
     *     android:layout_width="wrap_content"
     *     android:layout_height="wrap_content"
     *     app:enableSnapToSide="true">
     *
     *     <!-- Add your floating content here, e.g., a button or image -->
     *     <Button
     *         android:layout_width="wrap_content"
     *         android:layout_height="wrap_content"
     *         android:text="Drag me"/>
     *
     * </com.mohammadalmomani.modevlib.support.FloatingView>
     * ```
     * <p>
     * ### 2. Control Behavior Programmatically:
     * ```java
     * FloatingView floatingView = findViewById(R.id.floatingView);
     *
     * // Enable or disable snapping to screen sides
     * floatingView.setSnapToSide(true);  // or false
     *
     * // Optionally, handle clicks on the FloatingView or its children
     * floatingView.setOnClickListener(v -> {
     *     Toast.makeText(this, "FloatingView clicked!", Toast.LENGTH_SHORT).show();
     * });
     * ```
     * <p>
     * ## XML Attributes:
     * - `app:enableSnapToSide` → Boolean flag to enable/disable snapping behavior (default: true).
     * <p>
     * ## Notes:
     * - The view will stay within the horizontal screen bounds during dragging.
     * - Snap animation duration is 300ms.
     * - Distinguishes between click (tap) and drag based on touch duration.
     * - Child views inside the FloatingView will forward their touch events for dragging and clicking.
     */




    private float dX, dY;
    private int screenWidth;
    private int viewWidth;
    private boolean enableSnapToSide = true;
    private long startTime;
    private static final int CLICK_THRESHOLD = 200;

    public FloatingView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public FloatingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setOnTouchListener(this);

        // Optional: allow FloatingView to receive events
        setClickable(true);
        setFocusable(true);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingView);
            enableSnapToSide = a.getBoolean(R.styleable.FloatingView_enableSnapToSide, true);
            a.recycle();
        }

        post(() -> {
            viewWidth = getWidth();

            // Forward touch from children to this FloatingView
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.setOnTouchListener(this);
            }
        });
    }

    public void setSnapToSide(boolean enable) {
        this.enableSnapToSide = enable;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View parentView = this; // Always move the FloatingView itself

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = parentView.getX() - event.getRawX();
                dY = parentView.getY() - event.getRawY();
                startTime = System.currentTimeMillis();
                return true;

            case MotionEvent.ACTION_MOVE:
                float newX = event.getRawX() + dX;
                float newY = event.getRawY() + dY;

                // Prevent going out of horizontal screen
                newX = Math.max(0, Math.min(newX, screenWidth - viewWidth));

                parentView.setX(newX);
                parentView.setY(newY);
                return true;

            case MotionEvent.ACTION_UP:
                long clickDuration = System.currentTimeMillis() - startTime;
                if (clickDuration < CLICK_THRESHOLD) {
                    // If the original touch target was a child, let it handle the click
                    if (v != this) {
                        v.performClick();
                    } else {
                        performClick(); // fallback for FloatingView click
                    }
                } else if (enableSnapToSide) {
                    float centerX = screenWidth / 2f;
                    float targetX = (parentView.getX() + viewWidth / 2f) < centerX
                            ? 0
                            : (screenWidth - viewWidth);

                    parentView.animate()
                            .x(targetX)
                            .setDuration(300)
                            .start();
                }

            return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
