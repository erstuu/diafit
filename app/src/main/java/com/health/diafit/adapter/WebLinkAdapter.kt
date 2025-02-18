package com.health.diafit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.health.diafit.databinding.NewsItemBinding
import com.health.diafit.data.remote.response.WebLink
import coil.load

class WebLinkAdapter(private val onItemClick: (WebLink) -> Unit
) : ListAdapter<WebLink, WebLinkAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(webLink: WebLink) {
            binding.root.setOnClickListener {
                onItemClick(webLink)
            }
            binding.newsTitle.text = webLink.title
            binding.imageView.load(webLink.imageUrl)
            binding.newsDescription.text = webLink.description
        }
    }

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<WebLink>() {
        override fun areItemsTheSame(oldItem: WebLink, newItem: WebLink): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WebLink, newItem: WebLink): Boolean {
            return oldItem == newItem
        }

    }

}