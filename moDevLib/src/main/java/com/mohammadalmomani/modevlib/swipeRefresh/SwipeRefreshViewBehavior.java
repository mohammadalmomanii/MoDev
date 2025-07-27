package com.mohammadalmomani.modevlib.swipeRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class SwipeRefreshViewBehavior extends CoordinatorLayout.Behavior<View> {

    public SwipeRefreshViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof SwipeRefreshView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, final View child, View dependency) {
        if (dependency instanceof SwipeRefreshView swipeRefreshView) {
            swipeRefreshView.getAppBar().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) == 1) {
                        // AppBar fully expanded
                        int offset = swipeRefreshView.getHeight();
                        child.animate().translationY((float) offset).setDuration(300L).start();
                        Log.d("SwipeRefreshBehavior", "AppBar is fully expanded");
                    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                        // AppBar fully collapsed
                        child.animate().translationY(0f).setDuration(300L).start();
                        Log.d("SwipeRefreshBehavior", "AppBar is fully collapsed");
                    }
                }
            });
            return true;
        }
        return false;
    }
}
