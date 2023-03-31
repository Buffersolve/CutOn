package com.buffersolve.cuton.feature.catalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffersolve.cuton.databinding.CatalogRvItemBinding
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.X10
import com.bumptech.glide.Glide

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    var list: List<X10> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = CatalogRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = list.size

    inner class CatalogViewHolder(private val binding: CatalogRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {

                // Text
                tvBrand.text = list[bindingAdapterPosition].brandName

                // Img
                Glide.with(imageView.context)
                    .load(list[bindingAdapterPosition].brandImage)
                    .into(imageView)

                // Click Listener
//                root.setOnClickListener {
//                    onItemClickListener?.invoke(list[bindingAdapterPosition])
//                }
            }

        }

    }

    // Click Listener
//    private var onItemClickListener: ((X10) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (X10) -> Unit) {
//        onItemClickListener = listener
//    }


}
