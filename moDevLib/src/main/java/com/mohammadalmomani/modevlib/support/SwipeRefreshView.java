package com.mohammadalmomani.modevlib.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.SwipeRefreshViewBinding;

public class SwipeRefreshView extends AppBarLayout {

    /**
     * SwipeRefreshView is a custom `AppBarLayout` that integrates a swipe-to-refresh loading animation.
     * It allows for dynamic control over loading indicators and app bar expansion.
     * <p>
     * ## Features:
     * - Supports GIF-based loading animations.
     * - Expand/collapse the app bar dynamically.
     * - Disables user interaction during loading.
     * - Customizable via XML attributes.
     * <p>
     * ## How to Use:
     * <p>
     * ### 1. Add to XML Layout:
     * ```xml
     * <com.mohammadalmomani.modevlib.support.SwipeRefreshView
     *     android:layout_width="match_parent"
     *     android:layout_height="wrap_content"
     *     app:gifSrc="@drawable/loading_animation"
     *     app:expanded="true"/>
     * ```
     * <p>
     * ### 2. Control the Loading Programmatically:
     * ```java
     * SwipeRefreshView swipeRefreshView = findViewById(R.id.swipeRefreshView);
     * swipeRefreshView.setGif(R.drawable.custom_loading_gif); // Set custom loading GIF
     * swipeRefreshView.show(true); // Show loading (expands app bar and disables user interaction)
     * swipeRefreshView.show(false); // Hide loading (collapses app bar and re-enables interaction)
     * ```
     * <p>
     * ## XML Attributes:
     * - `app:gifSrc` → Sets the GIF animation resource.
     * - `app:expanded` → Determines whether the app bar is initially expanded.
     * <p>
     * ## Notes:
     * - Ensure the parent layout includes a `CoordinatorLayout` for proper behavior.
     * - Calling `show(true)` will prevent user interaction, while `show(false)` will restore it.
     * - The default loading GIF is `R.drawable.gif_preloader`, but it can be overridden.
     */


    private static SwipeRefreshViewBinding binding;
    private boolean isExpanded = false;

    public SwipeRefreshView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SwipeRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwipeRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    AppBarLayout getAppBar() {
        return binding.appBar;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.swipe_refresh_view, this, true);


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshView);

            // Load the GIF from XML
            int gifResId = typedArray.getResourceId(R.styleable.SwipeRefreshView_gifSrc, R.drawable.gif_preloader);
            setGif(gifResId);

            // Set the initial loading state from XML

            boolean expanded = typedArray.getBoolean(R.styleable.SwipeRefreshView_expanded, false);
            show(expanded);
            isExpanded = expanded;


            typedArray.recycle();
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setGif(int gifResId) {
        Glide.with(getContext()).load(gifResId).into(binding.imageView);
    }

    public void show(boolean b) {
        if (getContext() instanceof AppCompatActivity) {
            if (b) {
                ((AppCompatActivity) getContext()).getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                );
            } else {
                ((AppCompatActivity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
        binding.appBar.setExpanded(b, true);

    }
}
