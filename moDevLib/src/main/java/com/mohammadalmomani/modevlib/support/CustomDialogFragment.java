package com.mohammadalmomani.modevlib.support;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentCustomeDialogBinding;


public class CustomDialogFragment extends DialogFragment {

    static private FragmentCustomeDialogBinding binding;
    static private CustomDialogFragment fragment;
    static private MainInterface mainInterface;

    public CustomDialogFragment() {
        // Required empty public constructor
        dismissDialog();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mainInterface instanceof CustomDialogFragment)
            mainInterface = (MainInterface) context;
    }

    public static CustomDialogFragment newInstance() {
        fragment = new CustomDialogFragment();
        return fragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainInterface = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_12);

        binding = FragmentCustomeDialogBinding.inflate(inflater, container, false);

        AppHelper.setInvisible(binding.iv);
        AppHelper.setInvisible(binding.tvTitle);
        AppHelper.setInvisible(binding.btnNegative);
        AppHelper.setInvisible(binding.btnAdditional);
        AppHelper.setInvisible(binding.btnNeutral);
        AppHelper.setInvisible(binding.btnPositive);
        AppHelper.setGone(binding.layNote);
        AppHelper.setGone(binding.tvDescription);


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

    public CustomDialogFragment startShow(FragmentManager manager) {

        this.showNow(manager, "");
        return fragment;
    }

    public CustomDialogFragment setImage(Drawable drawable) {
        AppHelper.setVisible(binding.iv);
        binding.iv.setImageDrawable(drawable);
        return fragment;
    }

    public CustomDialogFragment setTitle(String title) {
        AppHelper.setVisible(binding.tvTitle);
        binding.tvTitle.setText(title);
        return fragment;
    }

    public CustomDialogFragment setDescription(String description) {
        AppHelper.setVisible(binding.tvDescription);
        binding.tvDescription.setText(description);
        return fragment;
    }

    public CustomDialogFragment setBtnPositive(String title, boolean isPassword, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnPositive);
        binding.btnPositive.setText(title);
        binding.btnPositive.setOnClickListener(v -> {

            mainInterface.onItemClick();
            if (binding.etNote.getVisibility() == View.VISIBLE && !binding.etNote.getText().toString().isEmpty() && !isPassword)
                dismissDialog();
        });
        return fragment;
    }

    public CustomDialogFragment setBtnNegative(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNegative);
        binding.btnNegative.setText(title);
        binding.btnNegative.setOnClickListener(v -> {

            mainInterface.onItemClick();
            dismissDialog();
        });
        return fragment;
    }

    public CustomDialogFragment setBtnAdditional(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnAdditional);
        binding.btnAdditional.setText(title);
        binding.btnAdditional.setOnClickListener(v -> {

            mainInterface.onItemClick();
            dismissDialog();
        });
        return fragment;
    }

    public CustomDialogFragment setNote() {
        AppHelper.setVisible(binding.layNote);

        return fragment;
    }

    public String getNote() {
        return binding.etNote.getText() + "";
    }

    public CustomDialogFragment setBtnNeutral(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNeutral);
        binding.btnNeutral.setText(title);
        binding.btnNeutral.setOnClickListener(v -> {

            mainInterface.onItemClick();
            dismissDialog();
        });
        return fragment;
    }


}