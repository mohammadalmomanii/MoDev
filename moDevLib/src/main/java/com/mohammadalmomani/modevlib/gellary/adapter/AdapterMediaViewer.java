package com.mohammadalmomani.modevlib.gellary.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.gson.Gson;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.gellary.GalleryImageFragment;
import com.mohammadalmomani.modevlib.gellary.GalleryVideoFragment;

import java.util.ArrayList;
import java.util.List;

public class AdapterMediaViewer extends FragmentStateAdapter {

    private List<Object> list = new ArrayList<>();

    public AdapterMediaViewer(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setList(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("createFragmentcreateFragment", "createFragment: " + new Gson().toJson(list.get(position)));
        if (list.get(position).toString().endsWith(".png") ||
                list.get(position).toString().endsWith(".jpg") ||
                list.get(position).toString().endsWith(".jpeg") ||
                list.get(position).toString().endsWith(".gif") ||
                list.get(position).toString().endsWith(".bmp") ||
                list.get(position).toString().endsWith(".webp") ||
                list.get(position).toString().endsWith(".heic") ||
                list.get(position).toString().endsWith(".heif") ||
                list.get(position).toString().endsWith(".tiff") ||
                list.get(position).toString().endsWith(".xml") ||
                list.get(position).toString().endsWith(".svg")) {

            // هذا مسار صورة صالحة
            return GalleryImageFragment.builder(list.get(position));

        } else if (
                list.get(position).toString().endsWith(".mp4") ||
                        list.get(position).toString().endsWith(".3gp") ||
                        list.get(position).toString().endsWith(".mkv") ||
                        list.get(position).toString().endsWith(".webm") ||
                        list.get(position).toString().endsWith(".avi") ||
                        list.get(position).toString().endsWith(".mov") ||
                        list.get(position).toString().endsWith(".flv") ||
                        list.get(position).toString().endsWith(".wmv") ||
                        list.get(position).toString().endsWith(".m4v")) {
            return GalleryVideoFragment.builder(list.get(position));

        } else return GalleryImageFragment.builder(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
