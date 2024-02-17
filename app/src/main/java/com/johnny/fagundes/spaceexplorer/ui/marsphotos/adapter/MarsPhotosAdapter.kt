package com.johnny.fagundes.spaceexplorer.ui.marsphotos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnny.fagundes.spaceexplorer.databinding.ItemMarsPhotosBinding
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhoto

class MarsPhotosAdapter(private val photos: List<MarsRoverPhoto>) :
    RecyclerView.Adapter<MarsPhotosAdapter.MarsPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMarsPhotosBinding.inflate(inflater, parent, false)
        return MarsPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class MarsPhotosViewHolder(private val binding: ItemMarsPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: MarsRoverPhoto) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }
}