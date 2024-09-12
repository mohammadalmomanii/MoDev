package com.mohammadalmomani.modevlib.imageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentImageViewerBinding;
import com.mohammadalmomani.modevlib.imageView.adapters.AdapterImageViewer;
import com.mohammadalmomani.modevlib.imageView.adapters.AdapterImages;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.MainInterface;
import com.mohammadalmomani.modevlib.topSheetDialog.TopSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageViewerFragment extends TopSheetDialogFragment implements MainInterface {
    private List<Object> imageList = new ArrayList<>();
    private FragmentImageViewerBinding binding;
    private AdapterImages adapter;
    private static ImageViewerFragment fragment;
    private static AdapterImageViewer vpAdapter;

    public ImageViewerFragment() {
        // Required empty public constructor
    }

    public static ImageViewerFragment getFragment() {
        return fragment;
    }

    public static ImageViewerFragment newInstance(List<Object> imageList) {
        dismissDialog();
        fragment = new ImageViewerFragment();
        fragment.imageList = imageList;
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.Modev_CustomBottomSheetDialogTheme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImageViewerBinding.inflate(inflater, container, false);


//        Glide.with(getActivity()).load(image).into(binding.ivZoomImage);
        AppHelper.expandedTopSheetDialog(this);


        adapter = new AdapterImages();

        adapter.setMainInterface(this);
        binding.ivClose.setOnClickListener(v -> {
            dismissDialog();
        });

        binding.rv.setAdapter(adapter);
        vpAdapter = new AdapterImageViewer(getActivity());
        binding.vp.setAdapter(vpAdapter);


        adapter.setList(imageList);
        vpAdapter.setList(imageList);
        binding.tvCounter.setText((1 + adapter.getSelectedItem()) + " of " + adapter.getItemCount());

        binding.ivLeft.setOnClickListener(v -> {
            int moveTo = adapter.getSelectedItem() - 1;
            if (moveTo >= 0) {
                onItemClick(new Object(), moveTo);
            }
        });
        binding.ivRight.setOnClickListener(v -> {
            int moveTo = adapter.getSelectedItem() + 1;//0+1
            if (moveTo < adapter.getItemCount()) {//10
                onItemClick(new Object(), moveTo);
            }
        });
        binding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                onItemClick(new Object(), position);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onItemClick(Object object, int position) {
        Log.d("asdfghgfd", "asderftgyh");
        if (adapter.getSelectedItem() != position) {
            adapter.setSelectedItem(position);
            binding.vp.setCurrentItem(position, true);
            binding.tvCounter.setText((1 + position) + " of " + adapter.getItemCount());
            binding.rv.smoothScrollToPosition(position);
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
    }

    public static void dismissDialog() {
        if (fragment != null)
            fragment.dismiss();
    }
}

