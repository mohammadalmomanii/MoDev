package com.mohammadalmomani.modevlib.imageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.FragmentImageViewerBinding;
import com.mohammadalmomani.modevlib.imageView.adapters.AdapterImageViewer;
import com.mohammadalmomani.modevlib.imageView.adapters.AdapterImages;
import com.mohammadalmomani.modevlib.support.AppHelper;
import com.mohammadalmomani.modevlib.support.CustomeDialog.CustomDialogFragment;
import com.mohammadalmomani.modevlib.support.MainInterface;

import java.util.ArrayList;
import java.util.List;

public class ImageViewerFragment extends BottomSheetDialogFragment implements MainInterface {


    /**
     * ImageViewerFragment is a BottomSheetDialogFragment used to display a list of images
     * in a ViewPager2 with navigation controls.
     *
     * <p>
     * This fragment supports viewing images in a scrollable view with left/right navigation buttons
     * and a page indicator.
     * </p>
     *
     * <h3>How to Use:</h3>
     * <pre>
     * // Create a list of images (URLs or drawable resources)
     * List<Object> imageList = new ArrayList<>();
     * imageList.add("https://example.com/image1.jpg");
     * imageList.add("https://example.com/image2.jpg");
     *
     * // Create a new instance of ImageViewerFragment and show it
     * ImageViewerFragment viewer = ImageViewerFragment.newInstance(imageList);
     * viewer.show(getSupportFragmentManager(), "image_viewer");
     * </pre>
     *
     * <p>
     * Additional customization options:
     * </p>
     * <ul>
     *     <li>Set custom style: <code>viewer.setStyleFlag(DialogFragment.STYLE_NORMAL, R.style.MyStyle);</code></li>
     *     <li>Make it non-cancelable: <code>viewer.setCancelableFlag(false);</code></li>
     * </ul>
     *
     * @author Mohammad Al Momani
     */


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
        setStyle(DialogFragment.STYLE_NORMAL, R.style.modev_styleImageViewer);
    }


    public ImageViewerFragment setStyleFlag(int style, int theme) {
        super.setStyle(style, theme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImageViewerBinding.inflate(inflater, container, false);


//        Glide.with(getActivity()).load(image).into(binding.ivZoomImage);
        AppHelper.expandedBottomSheetDialog(this);


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
        if (adapter.getSelectedItem() != position) {
            adapter.setSelectedItem(position);
            binding.vp.setCurrentItem(position, true);
            binding.tvCounter.setText((1 + position) + " of " + adapter.getItemCount());
            binding.rv.smoothScrollToPosition(position);
        }

    }
    public ImageViewerFragment setCancelableFlag (boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
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

