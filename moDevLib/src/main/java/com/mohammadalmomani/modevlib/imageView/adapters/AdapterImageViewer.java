package com.mohammadalmomani.modevlib.imageView.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mohammadalmomani.modevlib.imageView.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated Use {@link com.mohammadalmomani.modevlib.gellary.adapter.AdapterMediaViewer} instead
 */
@Deprecated
public class AdapterImageViewer extends FragmentStateAdapter {

    private List<Object> list=new ArrayList<>();

    public AdapterImageViewer(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setList(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ImageFragment.newInstance(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
