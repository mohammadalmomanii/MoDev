package com.mohammadalmomani.modevlib.gellary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mohammadalmomani.modevlib.R;
import com.mohammadalmomani.modevlib.databinding.ItemImageViewerBinding;
import com.mohammadalmomani.modevlib.support.MainInterface;

import java.util.ArrayList;
import java.util.List;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.VHolder> {
    private List<Object> list=new ArrayList<>();
    private MainInterface mainInterface;

    private int selectedItem;

    public AdapterItems() {
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void setMainInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

    public int getSelectedItem() {
        return selectedItem;
    }


    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageViewerBinding binding = ItemImageViewerBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new VHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        Object object = list.get(position);
        holder.binding(object, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class VHolder extends RecyclerView.ViewHolder {
        ItemImageViewerBinding binding;

        public VHolder(@NonNull ItemImageViewerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void binding(Object object, int position) {
            if (selectedItem == position) {
                binding.checkBox.setChecked(true);
                binding.view.setBackgroundResource(R.color.primary);
                binding.view.setAlpha(0.35F);
            } else {
                binding.checkBox.setChecked(false);
                binding.view.setBackgroundResource(R.color.white);
                binding.view.setAlpha(0);
            }

            binding.checkBox.setOnClickListener((v) -> {
                    mainInterface.onItemClick(object, position);
            });

            Glide.with(binding.imageView.getContext()).load(object).into(binding.imageView);

        }
    }
}


