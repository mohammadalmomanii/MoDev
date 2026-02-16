package com.mohammadalmomani.modevlib.photoPicker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohammadalmomani.modevlib.databinding.LayoutTakenImageBinding

class TakenImageAdapter(
    private val onTakenImageClickListener: OnTakenImageClickListener,
    private val maxSelection: Int = 1
) : ListAdapter<TakenImage, TakenImageAdapter.ViewHolder>(TakenImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val takenImage = getItem(position)
        holder.bind(takenImage, onTakenImageClickListener)
    }

    class ViewHolder(private val binding: LayoutTakenImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            takenImage: TakenImage,
            onTakenImageClickListener: OnTakenImageClickListener
        ) {
            Glide.with(binding.image.context).load(takenImage.file).into(binding.image)
            binding.data = takenImage
            binding.onClickEvent = onTakenImageClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LayoutTakenImageBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun addItem(newItem: TakenImage) {
        val currentList = currentList.toMutableList()
        currentList.add(newItem)
        submitList(currentList)
    }

    fun toggleSelection(takenImage: TakenImage) {
        val current = currentList.toMutableList()
        val index = current.indexOfFirst { it.file == takenImage.file }
        if (index == -1) return

        val selectedCount = current.count { it.isSelected }
        val item = current[index]

        if (!item.isSelected && selectedCount >= maxSelection) {
            onTakenImageClickListener.onSelectionLimitReached()
            return
        }

        current[index] = item.copy(isSelected = !item.isSelected)
        submitList(current)
    }

    class TakenImageDiffCallback : DiffUtil.ItemCallback<TakenImage>() {
        override fun areItemsTheSame(oldItem: TakenImage, newItem: TakenImage) = oldItem.file == newItem.file
        override fun areContentsTheSame(oldItem: TakenImage, newItem: TakenImage) = oldItem == newItem
    }

    interface OnTakenImageClickListener {
        fun onTakenImageClicked(takenImage: TakenImage)
        fun onSelectionLimitReached()
    }
}
