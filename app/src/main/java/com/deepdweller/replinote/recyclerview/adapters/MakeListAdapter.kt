package com.deepdweller.replinote.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepdweller.replinote.databinding.RecyclerListItemBinding
import com.deepdweller.replinote.recyclerview.ListItemListener
import com.deepdweller.replinote.recyclerview.viewholders.MakeListVH
import com.deepdweller.replinote.room.ListItem
import java.util.*

class MakeListAdapter(private val items: ArrayList<ListItem>, private val listener: ListItemListener) :
    RecyclerView.Adapter<MakeListVH>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MakeListVH, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeListVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerListItemBinding.inflate(inflater, parent, false)
        return MakeListVH(binding, listener)
    }
}