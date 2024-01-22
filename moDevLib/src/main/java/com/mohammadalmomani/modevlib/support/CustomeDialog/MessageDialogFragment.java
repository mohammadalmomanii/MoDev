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
import com.mohammadalmomani.modevlib.databinding.FragmentMessageBinding;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;
import com.mohammadalmomani.modevlib.support.StaticString;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetDialog;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetDialogFragment;


public class MessageDialogFragment extends DialogFragment {

    static private FragmentMessageBinding binding;
    static private MessageDialogFragment fragment;
    static private MainInterface mainInterface;

    public MessageDialogFragment() {
        // Required empty public constructor
        dismissDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mainInterface instanceof CustomDialogFragment)
            mainInterface = (MainInterface) context;
    }

    public static MessageDialogFragment newInstance() {
        fragment = new MessageDialogFragment();
        return fragment;
    }
    public MessageDialogFragment startShow(FragmentManager manager) {

        this.showNow(manager, "");
        return fragment;
    }
    public static MessageDialogFragment getFragment() {
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainInterface = null;
    }
    public static void dismissDialog() {
        if (fragment != null)
            fragment.dismiss();
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
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_message, container, false);

        AppHelper.setInvisible(binding.ivSmall);
        AppHelper.setGone(binding.ivBig);
        AppHelper.setInvisible(binding.tvMessage);
        AppHelper.setGone(binding.btnGotIt);



        return binding.getRoot();
    }









    public MessageDialogFragment setImage(Drawable drawable,StaticString size) {
        if (size.getId()== StaticString.SMALL.getId()) {
            AppHelper.setVisible(binding.ivSmall);
            Glide.with(getActivity()).load(drawable).into(binding.ivSmall);
        }else {
            AppHelper.setVisible(binding.ivBig);
            Glide.with(getActivity()).load(drawable).into(binding.ivBig);
        }
        return fragment;
    }

    public MessageDialogFragment setMessage(String title) {
        AppHelper.setVisible(binding.tvMessage);
        binding.tvMessage.setText(title);
        return fragment;
    }



    public MessageDialogFragment setBtnPositive(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnGotIt);
        binding.btnGotIt.setText(title);
        binding.btnGotIt.setOnClickListener(v -> {


            mainInterface.onItemClick();

        });
        return fragment;
    }






}