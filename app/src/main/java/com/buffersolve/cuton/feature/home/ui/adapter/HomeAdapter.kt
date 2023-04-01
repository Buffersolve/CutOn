package com.buffersolve.cuton.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffersolve.cuton.databinding.HomeRvItemBinding
import com.buffersolve.cuton.feature.home.data.remote.api.models.Items
import com.bumptech.glide.Glide

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list: List<Items> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = list.size

    inner class HomeViewHolder(private val binding: HomeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {

                // Text
                textView.text = list[bindingAdapterPosition].itemName

                // Img
                Glide.with(imageView.context)
                    .load(list[bindingAdapterPosition].itemImage)
                    .into(imageView)

                // Click Listener
                root.setOnClickListener {
                    onItemClickListener?.invoke(list[bindingAdapterPosition])
                }
            }

        }

    }

    // Click Listener
    private var onItemClickListener: ((Items) -> Unit)? = null

    fun setOnItemClickListener(listener: (Items) -> Unit) {
        onItemClickListener = listener
    }

}