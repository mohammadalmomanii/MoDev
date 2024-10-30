package com.mohammadalmomani.modevlib.support;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentLoadingBinding;

public class LoadingFragment extends DialogFragment {

    private FragmentLoadingBinding binding;
    private static LoadingFragment fragment;
    private long showTime = 0;
    private int gifLoading = 0;

    public LoadingFragment() {
        // Empty public constructor
    }

    /**
     * @deprecated Use {@link #builder()} instead for better clarity and modern usage.
     */
    @Deprecated
    public static LoadingFragment newInstance(int gifLoading, boolean isCancelable, long showTime) {
        fragment = new LoadingFragment();
        fragment.setCancelable(isCancelable);
        fragment.showTime = showTime;
        fragment.gifLoading = gifLoading;
        return fragment;
    }

    /**
     * @deprecated Use {@link #builder()} instead for better clarity and modern usage.
     */
    @Deprecated
    public static LoadingFragment newInstance() {
        fragment = new LoadingFragment();
        return fragment;
    }

    /**
     * Creates a new instance of LoadingFragment.
     * This is the preferred way to create a LoadingFragment instance.
     */
    public static LoadingFragment builder() {
        fragment = new LoadingFragment();
        return fragment;
    }

    /**
     * Sets the GIF loading image resource ID.
     * @param gifLoading Resource ID for the GIF loading image.
     * @return The LoadingFragment instance.
     */
    public LoadingFragment setGifLoading(int gifLoading) {
        this.gifLoading = gifLoading;
        return this;
    }

    /**
     * Sets whether the dialog can be canceled by the user.
     * @param isCancelable Boolean indicating if the dialog is cancelable.
     * @return The LoadingFragment instance.
     */
    public LoadingFragment setCancelableFlag(boolean isCancelable) {
        this.setCancelable(isCancelable);
        return this;
    }

    /**
     * Sets the time in milliseconds before the dialog is dismissed.
     * @param showTime Duration in milliseconds.
     * @return The LoadingFragment instance.
     */
    public LoadingFragment setShowTime(long showTime) {
        this.showTime = showTime;
        return this;
    }


    /**
     * Starts showing the dialog fragment using a specified tag.
     *
     * @param manager The FragmentManager to manage the dialog fragment.
     * @param tag     Optional tag for the fragment.
     */
    public void build(@NonNull FragmentManager manager, @Nullable String tag) {
        super.showNow(manager, tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_12);

        if (showTime != 0) {
            AppHelper.delay(LoadingFragment::dismissDialog, showTime);
        }

        if (gifLoading == 0) {
            AppHelper.setVisible(binding.progressBar);
            AppHelper.setGone(binding.imageView2);
        } else {
            Glide.with(this).load(gifLoading).into(binding.imageView2);
            AppHelper.setGone(binding.progressBar);
            AppHelper.setVisible(binding.imageView2);
        }

        return binding.getRoot();
    }

    public static void dismissDialog() {
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
    }
}
