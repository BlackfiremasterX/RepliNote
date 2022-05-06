package com.deepdweller.replinote.fragments

import com.deepdweller.replinote.R

class Search : ReplinoteFragment() {

    override fun getBackground() = R.drawable.search

    override fun getObservable() = model.searchResults
}