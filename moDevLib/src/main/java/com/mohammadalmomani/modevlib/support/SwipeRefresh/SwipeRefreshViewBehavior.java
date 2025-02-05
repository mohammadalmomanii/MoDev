package com.mohammadalmomani.modevlib.support.SwipeRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class SwipeRefreshViewBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    public SwipeRefreshViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        // This behavior depends on the SwipeRefreshView
        return dependency instanceof SwipeRefreshView;
    }
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (dependency instanceof SwipeRefreshView) {
            SwipeRefreshView swipeRefreshView = (SwipeRefreshView) dependency;
            swipeRefreshView.getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) == 1) {
                        // AppBar fully expanded
                        int offset = swipeRefreshView.getHeight();
                        child.animate().translationY(offset).setDuration(300).start(); // Smooth animation
                         Log.d("SAFDSAF", "AppBar is fully expanded");
                    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                        // AppBar fully collapsed
                        child.animate().translationY(0).setDuration(300).start(); // Smooth animation
                        Log.d("SAFDSAF", "AppBar is fully collapsed");
                    }
                }
            });


            return true;
        }
        return false;
    }
}