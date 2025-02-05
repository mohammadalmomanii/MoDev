package com.mohammadalmomani.modevlib.support.CustomeDialog;

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
import com.mohammadalmomani.modevlib.databinding.FragmentMessageBinding;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;

public class MessageDialogFragment extends DialogFragment {

    /**
     * MessageDialogFragment is a simple, customizable dialog fragment used for displaying
     * informational messages with an optional image and a single button.
     *
     * <h3>How to Use:</h3>
     * <pre>
     * // Create a new instance using the builder pattern
     * MessageDialogFragment dialog = MessageDialogFragment.builder()
     *     .setMessage("This is an important message.")
     *     .setBtnPositive("OK", () -> {
     *         // Handle button click
     *     });
     *
     * // Show the dialog
     * dialog.build(getSupportFragmentManager(), "message_dialog");
     * </pre>
     *
     * <p>
     * Additional customization options:
     * </p>
     * <ul>
     *     <li>Set an image: <code>dialog.setImage(drawable, MessageDialogStrings.SMALL);</code></li>
     *     <li>Make it non-cancelable: <code>dialog.setCancelableFlag(false);</code></li>
     *     <li>Auto close after delay: <code>dialog.setAutoClose(3000);</code> (closes after 3 seconds)</li>
     * </ul>
     *
     * @author Mohammad Al Momani
     */


    private static FragmentMessageBinding binding;
    private static MessageDialogFragment fragment;

    // Temporary storage for values if binding is not yet created
    private String pendingMessage;
    private Drawable pendingImage;
    private MessageDialogStrings pendingImageSize;
    private String pendingButtonTitle;
    private MainInterface.DialogListener pendingButtonListener;

    public MessageDialogFragment() {
        // Constructor
        // Ensures any previous instance of the dialog is dismissed
        dismissDialog();
    }

    /**
     * @deprecated Use {@link #builder()} instead for better clarity and modern usage.
     * This method creates a new instance of MessageDialogFragment.
     */
    @Deprecated
    public static MessageDialogFragment newInstance() {
        fragment = new MessageDialogFragment();
        return fragment;
    }

    /**
     * Creates a new instance of MessageDialogFragment.
     * This replaces the deprecated newInstance() for improved clarity.
     */
    public static MessageDialogFragment builder() {
        fragment = new MessageDialogFragment();
        return fragment;
    }

    /**
     * Dismisses the dialog if it is currently visible.
     */
    public static void dismissDialog() {
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    public static boolean isDialogShowing() {
        return fragment != null && fragment.isVisible();
    }

    /**
     * @deprecated Use {@link #build(FragmentManager, String)} instead.
     * This method starts showing the dialog fragment.
     */
    @Deprecated
    public MessageDialogFragment startShow(FragmentManager manager) {
        this.showNow(manager, "");
        return fragment;
    }

    /**
     * Replaces startShow(FragmentManager) method to improve clarity.
     * Starts showing the dialog fragment using a specified tag.
     */
    public void build(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }

    public void buildNow(@NonNull FragmentManager manager, @Nullable String tag) {
        super.showNow(manager, tag);
    }

    /**
     * Handles the cleanup process for the dialog when it is dismissed.
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
        binding = null;
    }

    /**
     * Creates and returns the view hierarchy associated with the dialog.
     * Handles inflating the layout and applying any pending properties.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);

        // Inflate the layout with DataBindingUtil
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);

        // Set default visibility for dialog elements
        AppHelper.setInvisible(binding.ivSmall);
        AppHelper.setGone(binding.ivBig);
        AppHelper.setInvisible(binding.tvMessage);
        AppHelper.setGone(binding.btnGotIt);


        return binding.getRoot();
    }

    public MessageDialogFragment setCancelableFlag(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    /**
     * Sets the image to be displayed in the dialog. The image size can be specified.
     * If the view is not ready, the image is stored temporarily until it can be applied.
     *
     * @param drawable The image to display
     * @param size     The size specification for the image (small or large)
     */
    public MessageDialogFragment setImage(Drawable drawable, MessageDialogStrings size) {
        if (binding != null) {
            if (size.getId() == MessageDialogStrings.SMALL.getId()) {
                AppHelper.setVisible(binding.ivSmall);
                Glide.with(getActivity()).load(drawable).into(binding.ivSmall);
            } else {
                AppHelper.setVisible(binding.ivBig);
                Glide.with(getActivity()).load(drawable).into(binding.ivBig);
            }
        } else {
            // Store image temporarily until binding is ready
            pendingImage = drawable;
            pendingImageSize = size;
        }
        return this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Apply pending properties if they were set before binding was ready
        if (pendingMessage != null) {
            setMessage(pendingMessage);
        }
        if (pendingImage != null && pendingImageSize != null) {
            setImage(pendingImage, pendingImageSize);
        }
        if (pendingButtonTitle != null) {
            setBtnPositive(pendingButtonTitle, pendingButtonListener);
        }

    }

    /**
     * Sets the message text to be displayed in the dialog.
     * If the view is not ready, the message is stored temporarily until it can be applied.
     *
     * @param title The message text to display
     */
    public MessageDialogFragment setMessage(String title) {
        if (binding != null) {
            AppHelper.setVisible(binding.tvMessage);
            binding.tvMessage.setText(title);

        } else {
            // Store message temporarily until binding is ready
            pendingMessage = title;
        }
        return this;
    }

    /**
     * Sets the positive button text and its click listener.
     * If the view is not ready, these are stored temporarily until they can be applied.
     *
     * @param title         Text to display on the button
     * @param mainInterface Interface for handling button click events
     */
    public MessageDialogFragment setBtnPositive(String title, @Nullable MainInterface.DialogListener mainInterface) {
        if (binding != null) {
            AppHelper.setVisible(binding.btnGotIt);
            binding.btnGotIt.setText(title);
            binding.btnGotIt.setOnClickListener(v -> {
                if (mainInterface != null) {
                    mainInterface.onItemClick();
                }
                dismissDialog();

            });
        } else {
            // Store button properties temporarily until binding is ready
            pendingButtonTitle = title;
            pendingButtonListener = mainInterface;
        }
        return this;
    }

    public MessageDialogFragment setAutoClose(long after) {
        AppHelper.delay(() -> dismissDialog(), after);
        return this;
    }


    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
    }

    public enum MessageDialogStrings {
        SMALL(1, ""),
        BIG(2, "");

        private int id;
        private String text;

        MessageDialogStrings(int id, String text) {
            this.id = id;
            this.text = text;

        }

        MessageDialogStrings() {
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }

}
