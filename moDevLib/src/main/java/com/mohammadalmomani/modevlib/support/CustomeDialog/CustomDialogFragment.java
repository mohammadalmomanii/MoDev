package com.mohammadalmomani.modevlib.support.CustomeDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
import com.mohammadalmomani.modevlib.databinding.FragmentCustomDialogBinding;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;

public class CustomDialogFragment extends DialogFragment {

    private static FragmentCustomDialogBinding binding;
    private static CustomDialogFragment fragment;
    private static MainInterface mainInterface;

    // Temporary storage for values if binding is not yet created
    private String pendingTitle;
    private String pendingDescription;
    private Drawable pendingImage;
    private String pendingPositiveButtonTitle;
    private String pendingNegativeButtonTitle;
    private String pendingNeutralButtonTitle;
    private MainInterface pendingPositiveButtonListener;
    private MainInterface pendingNegativeButtonListener;
    private MainInterface pendingNeutralButtonListener;

    // Flag to determine if the dialog is for password input

    public CustomDialogFragment() {
        // Required empty public constructor
        dismissDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainInterface) {
            mainInterface = (MainInterface) context;
        }
    }

    /**
     * Creates a new instance of CustomDialogFragment.
     * This replaces the deprecated newInstance() for improved clarity.
     */
    public static CustomDialogFragment builder() {
        fragment = new CustomDialogFragment();
        return fragment;
    }

    /**
     * @deprecated Use {@link #builder()} instead for better clarity and modern usage.
     */
    @Deprecated
    public static CustomDialogFragment newInstance() {
        fragment = new CustomDialogFragment();
        return fragment;
    }

    /**
     * Starts showing the dialog fragment using a specified tag.
     *
     * @param manager The FragmentManager to manage the dialog fragment.
     * @param tag     Optional tag for the fragment.
     */
    public void build(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }
    public void buildNow(@NonNull FragmentManager manager, @Nullable String tag) {
        super.showNow(manager, tag);
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
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_12);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dialog, container, false);

        // Set default visibility for dialog elements
        AppHelper.setInvisible(binding.iv);
        AppHelper.setInvisible(binding.tvTitle);
        AppHelper.setGone(binding.btnNegative);
        AppHelper.setGone(binding.btnNeutral);
        AppHelper.setGone(binding.btnPositive);
        AppHelper.setGone(binding.tvDescription);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Apply pending properties if they were set before binding was ready
        if (pendingTitle != null) {
            setTitle(pendingTitle);
        }
        if (pendingDescription != null) {
            setDescription(pendingDescription);
        }
        if (pendingImage != null) {
            setImage(pendingImage);
        }
        if (pendingPositiveButtonTitle != null) {
            setBtnPositive(pendingPositiveButtonTitle, pendingPositiveButtonListener);
        }
        if (pendingNegativeButtonTitle != null) {
            setBtnNegative(pendingNegativeButtonTitle, pendingNegativeButtonListener);
        }
        if (pendingNeutralButtonTitle != null) {
            setBtnNeutral(pendingNeutralButtonTitle, pendingNeutralButtonListener);
        }
    }


    public CustomDialogFragment setCancelableFlag (boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    public CustomDialogFragment setImage(Drawable drawable) {
        if (binding != null) {
            AppHelper.setVisible(binding.iv);
            Glide.with(requireActivity()).load(drawable).into(binding.iv);
        } else {
            pendingImage = drawable; // Store temporarily if binding is not ready
        }
        return this;
    }

    public CustomDialogFragment setTitle(String title) {
        if (binding != null) {
            AppHelper.setVisible(binding.tvTitle);
            binding.tvTitle.setText(title);
        } else {
            pendingTitle = title; // Store temporarily if binding is not ready
        }
        return this;
    }

    public CustomDialogFragment setDescription(String description) {
        if (binding != null) {
            AppHelper.setVisible(binding.tvDescription);
            binding.tvDescription.setText(description);
        } else {
            pendingDescription = description; // Store temporarily if binding is not ready
        }
        return this;
    }

    public CustomDialogFragment setBtnPositive(String title, MainInterface listener) {
        if (binding != null) {
            AppHelper.setVisible(binding.btnPositive);
            binding.btnPositive.setText(title);
            binding.btnPositive.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick();
                }
            });
        } else {
            pendingPositiveButtonTitle = title; // Store temporarily if binding is not ready
            pendingPositiveButtonListener = listener;
        }
        return this;
    }

    public CustomDialogFragment setBtnNegative(String title, MainInterface listener) {
        if (binding != null) {
            AppHelper.setVisible(binding.btnNegative);
            binding.btnNegative.setText(title);
            binding.btnNegative.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick();
                }
            });
        } else {
            pendingNegativeButtonTitle = title; // Store temporarily if binding is not ready
            pendingNegativeButtonListener = listener;
        }
        return this;
    }


    public CustomDialogFragment setBtnNeutral(String title, MainInterface listener) {
        if (binding != null) {
            AppHelper.setVisible(binding.btnNeutral);
            binding.btnNeutral.setText(title);
            binding.btnNeutral.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick();
                }
            });
        } else {
            pendingNeutralButtonTitle = title; // Store temporarily if binding is not ready
            pendingNeutralButtonListener = listener;
        }
        return this;
    }


    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
    }


}
