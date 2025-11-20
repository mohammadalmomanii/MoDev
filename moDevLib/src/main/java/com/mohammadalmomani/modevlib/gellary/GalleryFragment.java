package com.mohammadalmomani.modevlib.gellary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentGalleryBinding;
import com.mohammadalmomani.modevlib.gellary.adapter.AdapterItems;
import com.mohammadalmomani.modevlib.gellary.adapter.AdapterMediaViewer;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends BottomSheetDialogFragment implements MainInterface {

    private static GalleryFragment fragment;
    private static AdapterMediaViewer vpAdapter;
    private List<Object> mediaList = new ArrayList<>();
    private int startPosition = 0;
    //private Boolean showSizeButton = false;
    private FragmentGalleryBinding binding;
    private AdapterItems adapter;

    public GalleryFragment() {
    }

    // -----------------------------------------------------
    //                DEPRECATED NEW INSTANCE
    // -----------------------------------------------------

    /**
     * @deprecated Use ImageViewerFragment.Builder instead
     */
    @Deprecated
    public static GalleryFragment newInstance(List<Object> imageList) {
        dismissDialog();
        fragment = new GalleryFragment();
        fragment.mediaList = imageList;
        fragment.startPosition = 0;
        return fragment;
    }

    public static void dismissDialog() {
        if (fragment != null)
            fragment.dismiss();
    }

    // -----------------------------------------------------
    //                      LIFECYCLE
    // -----------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.modev_styleImageViewer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        AppHelper.expandedBottomSheetDialog(this);

        adapter = new AdapterItems();
        adapter.setMainInterface(this);

        binding.rv.setAdapter(adapter);

        vpAdapter = new AdapterMediaViewer(getActivity());
        binding.vp.setAdapter(vpAdapter);

        adapter.setList(mediaList);
        vpAdapter.setList(mediaList);

        // ------------------ INITIAL POSITION ------------------
        onItemClick(new Object(), startPosition);
        binding.vp.setCurrentItem(startPosition, false);

        // ------------------ CLOSE BUTTON ----------------------
        binding.ivClose.setOnClickListener(v -> dismissDialog());

        // ------------------ LEFT BUTTON -----------------------
        binding.ivLeft.setOnClickListener(v -> {
            int moveTo = adapter.getSelectedItem() - 1;
            if (moveTo >= 0) onItemClick(new Object(), moveTo);
        });
        // ------------------ SIZE BUTTON -----------------------
//        if (!showSizeButton)
//            AppHelper.setGone(binding.ivSizeController);
//        binding.ivSizeController.setOnClickListener(v -> {
//
//            ViewGroup.LayoutParams params = binding.vp.getLayoutParams();
//
//            // If it is not full screen → make it full screen
//            if (params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
//                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            }
//            // If already full screen → return to original size
//            else {
//                params.height = getResources().getDimensionPixelSize(R.dimen._170mdp);
//                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            }
//
//            binding.vp.setLayoutParams(params);
//        });


        // ------------------ RIGHT BUTTON ----------------------
        binding.ivRight.setOnClickListener(v -> {
            int moveTo = adapter.getSelectedItem() + 1;
            if (moveTo < adapter.getItemCount()) onItemClick(new Object(), moveTo);
        });

        // ------------------ SWIPE EVENTS -----------------------
        binding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                onItemClick(new Object(), position);
            }
        });

        return binding.getRoot();
    }

    // -----------------------------------------------------
    //                  ITEM CLICK HANDLER
    // -----------------------------------------------------

    @Override
    public void onItemClick(Object object, int position) {
        if (adapter.getSelectedItem() != position) {

            adapter.setSelectedItem(position);
            binding.vp.setCurrentItem(position, true);

            binding.tvCounter.setText((1 + position) + " " + getString(R.string.of) + " " + adapter.getItemCount());

            binding.rv.smoothScrollToPosition(position);
        }
    }

    // -----------------------------------------------------
    //                  DISMISS HANDLING
    // -----------------------------------------------------

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

    // -----------------------------------------------------
    //                     BUILDER
    // -----------------------------------------------------
    public static class Builder {
        private final List<Object> images = new ArrayList<>();
        private int position = 0;
        private int style = R.style.modev_styleImageViewer;
        private boolean cancelable = true;
//        private boolean showSizeButton = false;

        public Builder setImages(List<Object> list) {
            if (list != null) images.addAll(list);
            return this;
        }

        public Builder setStartPosition(int pos) {
            this.position = pos;
            return this;
        }

//        public Builder showSizeButton(boolean show) {
//            this.showSizeButton = show;
//            return this;
//        }

        public Builder setStyle(int styleRes) {
            this.style = styleRes;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public GalleryFragment build() {
            dismissDialog();
            fragment = new GalleryFragment();
            fragment.mediaList = new ArrayList<>(images);

            // Fix position bounds
            int max = images.size() - 1;
            fragment.startPosition = Math.max(0, Math.min(position, max));
//            fragment.showSizeButton = showSizeButton;

            fragment.setStyle(DialogFragment.STYLE_NORMAL, style);
            fragment.setCancelable(cancelable);

            return fragment;
        }

        public void show(@NonNull FragmentManager fm) {
            GalleryFragment f = build();
            f.show(fm, "image_viewer");
        }
    }
}
