package com.deepdweller.replinote.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.deepdweller.replinote.databinding.RecyclerLabelBinding
import com.deepdweller.replinote.recyclerview.ItemListener

class LabelVH(private val binding: RecyclerLabelBinding, listener: ItemListener) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            listener.onClick(adapterPosition)
        }

        binding.root.setOnLongClickListener {
            listener.onLongClick(adapterPosition)
            return@setOnLongClickListener true
        }
    }

    fun bind(value: String) {
        binding.root.text = value
    }
}