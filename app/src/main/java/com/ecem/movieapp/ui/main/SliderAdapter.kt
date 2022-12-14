package com.ecem.movieapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecem.movieapp.databinding.SliderItemBinding
import com.ecem.movieapp.data.model.Movies

class SliderAdapter(
    val context: Context,
    private val movieViewPager: ViewPagerClickListener
    ): RecyclerView.Adapter<SliderAdapter.ViewHolder>(){

    private lateinit var recyclerView: RecyclerView
    var items: List<Movies> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SliderItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        item.let {
            holder.apply {
                bind(item, isLinearLayoutManager())
                itemView.tag = item
            }
        }

        holder.itemView.setOnClickListener {
            movieViewPager.clickOnItem(
                item,
                holder.itemView)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemList: List<Movies>){
        items = itemList
        notifyDataSetChanged()
    }

    private fun isLinearLayoutManager() = recyclerView.layoutManager is LinearLayoutManager

    class ViewHolder(private val binding: SliderItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movies, isLinearLayoutManager: Boolean) {
            binding.apply {
                movies = item
            }
        }
    }
}