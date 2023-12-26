package com.mohammadalmomani.modevlib.imageView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mohammadalmomani.modevlib.databinding.FragmentImageBinding;


public class ImageFragment extends Fragment {


    private FragmentImageBinding binding;

    private static Object image;


    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(Object image) {
        ImageFragment fragment = new ImageFragment();
        ImageFragment.image = image;
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
        binding = FragmentImageBinding.inflate(inflater, container, false);
        Glide.with(getActivity()).load(image).into(binding.ivZoomImage);
        return binding.getRoot();
    }
}