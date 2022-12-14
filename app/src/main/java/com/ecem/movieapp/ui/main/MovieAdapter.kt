package com.ecem.movieapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecem.movieapp.databinding.ItemMoviesBinding
import com.ecem.movieapp.data.model.Movies

class MovieAdapter(val context: Context, private val moviesRecyclerView: RecyclerViewClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    var items: List<Movies> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoviesBinding.inflate(LayoutInflater.from(parent.context),
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
            moviesRecyclerView.clickOnItem(
                item,
                holder.itemView
            )
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int { return items.size }

    fun submitList(itemList: List<Movies>){
        items = itemList
        notifyDataSetChanged()
    }

    private fun isLinearLayoutManager() = recyclerView.layoutManager is LinearLayoutManager

    class ViewHolder(private val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movies, isLinearLayoutManager: Boolean) {
            binding.apply {
                movie = item
            }
        }
    }

    fun addData(listItems: ArrayList<Movies>) {
        val size = listItems.size
        listItems.addAll(listItems)
        val sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }
}