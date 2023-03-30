package com.buffersolve.cuton.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffersolve.cuton.databinding.HomeRvItemBinding
import com.buffersolve.cuton.feature.home.data.remote.api.models.Items
import com.bumptech.glide.Glide

class HomeAdapter(
//    private val list: List<Items>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val list = listOf(
        Items(
            1,
            "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png",
            "Каталог брендів"
        ),
        Items(
            2,
            "https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png",
            "Каталог брендів"
        )
    )

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

                textView.text = list[bindingAdapterPosition].itemName

                // Img
                Glide.with(imageView.context)
                    .load(list[bindingAdapterPosition].itemImage)
                    .into(imageView)
            }
        }

    }
}