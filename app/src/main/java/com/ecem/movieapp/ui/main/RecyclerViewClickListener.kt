package com.ecem.movieapp.ui.main

import android.view.View
import com.ecem.movieapp.data.model.Movies

interface RecyclerViewClickListener {
    fun clickOnItem(data: Movies, card: View)
}