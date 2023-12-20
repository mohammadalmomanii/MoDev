package com.mohammadalmomani.modevlib.support;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentLoadingBinding;


public class LoadingFragment extends DialogFragment {

    private FragmentLoadingBinding binding;
    private static LoadingFragment fragment;
    private static long showTime;


    public LoadingFragment() {
    }


    public static LoadingFragment newInstance(boolean isCancelable, long showTime) {
        fragment = new LoadingFragment();
        fragment.setCancelable(isCancelable);
        fragment.showTime = showTime;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_12);
        AppHelper.delay(() -> dismissDialog(), showTime);
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

}

