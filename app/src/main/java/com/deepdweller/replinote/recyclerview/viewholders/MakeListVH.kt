package com.deepdweller.replinote.recyclerview.viewholders

import android.text.InputType
import android.view.MotionEvent
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.deepdweller.replinote.databinding.RecyclerListItemBinding
import com.deepdweller.replinote.miscellaneous.setOnNextAction
import com.deepdweller.replinote.recyclerview.ListItemListener
import com.deepdweller.replinote.room.ListItem

class MakeListVH(val binding: RecyclerListItemBinding, listener: ListItemListener) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ListItem.setOnNextAction {
            listener.onMoveToNext(adapterPosition)
        }

        binding.ListItem.doAfterTextChanged { text ->
            listener.afterTextChange(adapterPosition, text.toString().trim())
        }

        binding.CheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.ListItem.paint.isStrikeThruText = isChecked
            binding.ListItem.isEnabled = !isChecked

            listener.onCheckedChange(adapterPosition, isChecked)
        }

        binding.DragHandle.setOnTouchListener { v, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                listener.onStartDrag(this)
            }
            false
        }
    }

    fun bind(item: ListItem) {
        binding.ListItem.setText(item.body)
        binding.CheckBox.isChecked = item.checked
        binding.ListItem.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
    }
}