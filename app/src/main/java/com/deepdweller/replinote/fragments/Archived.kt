package com.deepdweller.replinote.fragments

import com.deepdweller.replinote.R


class Archived : ReplinoteFragment() {

    override fun getBackground() = R.drawable.archive

    override fun getObservable() = model.archivedNotes
}