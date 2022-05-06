package com.deepdweller.replinote.fragments

import androidx.lifecycle.LiveData
import com.deepdweller.replinote.R
import com.deepdweller.replinote.miscellaneous.Constants
import com.deepdweller.replinote.room.Item


class DisplayLabel : ReplinoteFragment() {

    override fun getBackground() = R.drawable.label

    override fun getObservable(): LiveData<List<Item>> {
        val label = requireNotNull(requireArguments().getString(Constants.SelectedLabel))
        return model.getNotesByLabel(label)
    }
}