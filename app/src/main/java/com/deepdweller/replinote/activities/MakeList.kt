package com.deepdweller.replinote.activities

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deepdweller.replinote.databinding.ActivityMakeListBinding
import com.deepdweller.replinote.miscellaneous.bindLabels
import com.deepdweller.replinote.miscellaneous.getLocale
import com.deepdweller.replinote.miscellaneous.setOnNextAction
import com.deepdweller.replinote.recyclerview.ListItemListener
import com.deepdweller.replinote.recyclerview.adapters.MakeListAdapter
import com.deepdweller.replinote.recyclerview.viewholders.MakeListVH
import com.deepdweller.replinote.room.ListItem
import com.deepdweller.replinote.room.Type
import com.deepdweller.replinote.viewmodels.BaseNoteModel

import java.util.*

class MakeList : ReplinoteActivity() {

    private lateinit var adapter: MakeListAdapter

    override val type = Type.LIST
    override val binding by lazy { ActivityMakeListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.EnterTitle.setOnNextAction {
            moveToNext(-1)
        }

        setupListeners()
        setupRecyclerView()
        setupToolbar(binding.Toolbar)

        if (model.isNewNote) {
            if (model.items.isEmpty()) {
                addListItem()
            }
        }

        binding.AddItem.setOnClickListener {
            addListItem()
        }

        setStateFromModel()
    }


    override fun getLabelGroup() = binding.LabelGroup


    private fun addListItem() {
        val position = model.items.size
        val listItem = ListItem(String(), false)
        model.items.add(listItem)
        adapter.notifyItemInserted(position)
        binding.RecyclerView.post {
            val viewHolder = binding.RecyclerView.findViewHolderForAdapterPosition(position) as MakeListVH?
            viewHolder?.binding?.ListItem?.requestFocus()
        }
    }

    private fun setupListeners() {
        binding.EnterTitle.doAfterTextChanged { text -> model.title = text.toString().trim() }
    }


    private fun setupRecyclerView() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipe = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(drag, swipe)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model.items.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                Collections.swap(model.items, viewHolder.adapterPosition, target.adapterPosition)
                adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }
        })

        adapter = MakeListAdapter(model.items, object : ListItemListener {

            override fun onMoveToNext(position: Int) {
                moveToNext(position)
            }

            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }

            override fun afterTextChange(position: Int, text: String) {
                model.items[position].body = text
            }

            override fun onCheckedChange(position: Int, checked: Boolean) {
                model.items[position].checked = checked
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.RecyclerView)

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setStateFromModel() {
        val formatter = BaseNoteModel.getDateFormatter(getLocale())

        binding.EnterTitle.setText(model.title)
        binding.DateCreated.text = formatter.format(model.timestamp)

        binding.LabelGroup.bindLabels(model.labels)
    }

    private fun moveToNext(currentPosition: Int) {
        val viewHolder = binding.RecyclerView.findViewHolderForAdapterPosition(currentPosition + 1) as MakeListVH?
        if (viewHolder != null) {
            if (viewHolder.binding.CheckBox.isChecked) {
                moveToNext(currentPosition + 1)
            } else viewHolder.binding.ListItem.requestFocus()
        } else addListItem()
    }
}