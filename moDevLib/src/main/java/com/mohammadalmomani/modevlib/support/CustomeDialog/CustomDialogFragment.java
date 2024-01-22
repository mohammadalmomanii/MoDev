package com.mohammadalmomani.modevlib.support.CustomeDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentCustomDialogBinding;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;


public class CustomDialogFragment extends DialogFragment {

    static  private FragmentCustomDialogBinding binding;
    static private CustomDialogFragment fragment;
    static private MainInterface mainInterface;
    private boolean isPassword = false;

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
    public CustomDialogFragment startShow(FragmentManager manager,int dialogType) {

        this.showNow(manager, "");
        return fragment;
    }
    public static CustomDialogFragment getFragment() {
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainInterface = null;
    }
    public static void dismissDialog() {
        if (fragment != null&&!fragment.isPassword) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_custom_dialog, container, false);

        AppHelper.setInvisible(binding.iv);
        AppHelper.setInvisible(binding.tvTitle);
        AppHelper.setGone(binding.btnNegative);
        AppHelper.setGone(binding.btnNeutral);
        AppHelper.setGone(binding.btnPositive);
        AppHelper.setGone(binding.layNote);
        AppHelper.setGone(binding.tvDescription);


        return binding.getRoot();
    }







    public CustomDialogFragment setImage(Drawable drawable) {
        AppHelper.setVisible(binding.iv);
        Glide.with(getActivity()).load(drawable).into(binding.iv);
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

    public CustomDialogFragment setBtnPositive(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnPositive);
        binding.btnPositive.setText(title);
        binding.btnPositive.setOnClickListener(v -> {


                mainInterface.onItemClick();

        });
        return fragment;
    }

    public CustomDialogFragment setBtnNegative(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNegative);
        binding.btnNegative.setText(title);
        binding.btnNegative.setOnClickListener(v -> {


                mainInterface.onItemClick();
        });
        return fragment;
    }


    public CustomDialogFragment setNote(boolean isPassword) {
        AppHelper.setVisible(binding.layNote);
        this.isPassword = isPassword;

        return fragment;
    }

    public static String getNote() {
        return binding.etNote.getText() + "";
    } public static void setErrorMessage(String message) {
        binding.etNote.setError(message);

    }

    public CustomDialogFragment setBtnNeutral(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNeutral);
        binding.btnNeutral.setText(title);
        binding.btnNeutral.setOnClickListener(v -> {


                mainInterface.onItemClick();
        });
        return fragment;
    }


}