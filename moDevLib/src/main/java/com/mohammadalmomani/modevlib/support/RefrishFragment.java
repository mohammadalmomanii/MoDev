package com.mohammadalmomani.modevlib.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentRefrishBinding;

/**
 * HOW TO USE
 * In Activity {
 * repairing -> RefrishFragment.newInstance(getSupportFragmentManager(), linearLayout);
 * show -> RefrishFragment.showLoading(getDrawable(R.drawable.ic_launcher_background));
 * hide -> RefrishFragment.hideLoading();
 * <p>
 * <p>
 * }
 * <p>
 * <p>
 * In Activity XML{
 * <?xml version="1.0" encoding="utf-8"?>
 * <androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * xmlns:tools="http://schemas.android.com/tools"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * tools:context=".RefrishFragment">
 * <p>
 * <com.google.android.material.appbar.AppBarLayout
 * android:id="@+id/test"
 * android:layout_width="match_parent"
 * android:layout_height="145dp"
 * app:expanded="false">
 * <p>
 * <com.google.android.material.appbar.CollapsingToolbarLayout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * app:contentScrim="@color/primary"
 * app:layout_scrollFlags="scroll|enterAlways|exitUntilCollapsed" >
 * <p>
 * <ImageView
 * android:id="@+id/imageView"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * tools:src="@tools:sample/avatars" />
 * </com.google.android.material.appbar.CollapsingToolbarLayout>
 * <p>
 * </com.google.android.material.appbar.AppBarLayout>
 * <p>
 * <your layout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:orientation="horizontal"
 * app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">
 * .
 * .
 * .
 * .
 * </your layout>
 * </androidx.coordinatorlayout.widget.CoordinatorLayout>
 * }
 **/

public class RefrishFragment extends Fragment {

    private static FragmentRefrishBinding binding;
    private static AppBarLayout appBarLayout;
    private static ImageView imageView;
    private static AppCompatActivity activity;
    private static RefrishFragment fragment;

    public RefrishFragment() {
        // Required empty public constructor
    }

    public static RefrishFragment newInstance(AppCompatActivity activity, AppBarLayout appBarLayout, ImageView imageView) {
        fragment = new RefrishFragment();
        FragmentTransaction fragmentTransaction = fragment.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(appBarLayout.getId(), fragment).commitNow();
        RefrishFragment.appBarLayout = appBarLayout;
        RefrishFragment.imageView = imageView;
        RefrishFragment.activity = activity;
        Glide.with(fragment).load(R.drawable.gif_preloader).into(imageView);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_refrish, container, false);

        return binding.getRoot();
    }


    static public void setGif(int gif) {
        Glide.with(fragment).load(gif).into(imageView);
    }

    static public void showLoading() {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        appBarLayout.setExpanded(true, true);
    }

    static public void hideLoading() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        appBarLayout.setExpanded(false, true);
    }
}