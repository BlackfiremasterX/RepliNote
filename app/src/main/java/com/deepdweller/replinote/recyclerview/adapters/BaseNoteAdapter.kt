package com.deepdweller.replinote.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepdweller.replinote.databinding.RecyclerBaseNoteBinding
import com.deepdweller.replinote.databinding.RecyclerHeaderBinding
import com.deepdweller.replinote.helpers.SettingsHelper
import com.deepdweller.replinote.recyclerview.ItemListener
import com.deepdweller.replinote.recyclerview.viewholders.BaseNoteVH
import com.deepdweller.replinote.recyclerview.viewholders.HeaderVH
import com.deepdweller.replinote.room.BaseNote
import com.deepdweller.replinote.room.Header
import com.deepdweller.replinote.room.Item
import com.deepdweller.replinote.room.ViewType
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat

class BaseNoteAdapter(
    private val settingsHelper: SettingsHelper,
    private val formatter: SimpleDateFormat,
    private val listener: ItemListener
) : ListAdapter<Item, RecyclerView.ViewHolder>(DiffCallback) {

    private val prettyTime = PrettyTime()

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Header -> (holder as HeaderVH).bind(item)
            is BaseNote -> (holder as BaseNoteVH).bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ViewType.values()[viewType]) {
            ViewType.NOTE -> {
                val binding = RecyclerBaseNoteBinding.inflate(inflater, parent, false)
                BaseNoteVH(binding, settingsHelper, listener, prettyTime, formatter, inflater)
            }
            ViewType.HEADER -> {
                val binding = RecyclerHeaderBinding.inflate(inflater, parent, false)
                HeaderVH(binding)
            }
        }
    }


    private object DiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return when (oldItem) {
                is BaseNote -> if (newItem is BaseNote) {
                    oldItem.id == newItem.id
                } else false
                is Header -> if (newItem is Header) {
                    oldItem.label == newItem.label
                } else false
            }
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return when (oldItem) {
                is BaseNote -> if (newItem is BaseNote) {
                    oldItem == newItem
                } else false
                is Header -> if (newItem is Header) {
                    oldItem.label == newItem.label
                } else false
            }
        }
    }
}