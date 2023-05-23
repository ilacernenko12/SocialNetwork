package com.example.socialnetwork.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialnetwork.database.entity.PhotoModel
import com.example.socialnetwork.databinding.PhotoRecyclerViewBinding
import com.example.socialnetwork.screens.HomeActivity

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private var photoList: List<PhotoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photoList[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        Log.d("photoListSize = ", photoList.size.toString())
        return photoList.size
    }

    fun submitList(photos: List<PhotoModel>) {
        photoList = photos
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val binding: PhotoRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoModel) {
            Glide.with(binding.root)
                .load(photo.imagePath)
                .into(binding.ivPhoto)
        }
    }
}