package com.deepdweller.replinote.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.deepdweller.replinote.MenuDialog
import com.deepdweller.replinote.R
import com.deepdweller.replinote.activities.MainActivity
import com.deepdweller.replinote.activities.MakeList
import com.deepdweller.replinote.activities.TakeNote
import com.deepdweller.replinote.miscellaneous.add

class Notes : ReplinoteFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (requireContext() as MainActivity).binding.TakeNoteFAB.setOnClickListener {
            displayNoteTypes()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(R.string.search, R.drawable.search) { findNavController().navigate(R.id.NotesToSearch) }
    }


    private fun displayNoteTypes() {
        MenuDialog(requireContext())
            .add(R.string.make_list, R.drawable.checkbox) { goToActivity(MakeList::class.java) }
            .add(R.string.take_note, R.drawable.edit) { goToActivity(TakeNote::class.java) }
            .show()
    }


    override fun getObservable() = model.baseNotes

    override fun getBackground() = R.drawable.notes
}