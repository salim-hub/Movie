package com.ecem.movieapp.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ecem.movieapp.common.Constants.API_KEY
import com.ecem.movieapp.common.Constants.EXTRA_MOVIE
import com.ecem.movieapp.databinding.ActivityMainBinding
import com.ecem.movieapp.data.model.Movies
import com.ecem.movieapp.ui.detail.DetailActivity
import com.ecem.movieapp.common.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecyclerViewClickListener, ViewPagerClickListener {

    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MoviesViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this, this@MainActivity) }
    private val sliderAdapter: SliderAdapter by lazy { SliderAdapter(this, this@MainActivity) }
    private var list: ArrayList<Movies> = ArrayList()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            recyclerView.apply {
                adapter = movieAdapter

                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                viewPager.adapter = sliderAdapter
                val indicator = circleIndicator
                indicator.setViewPager(viewPager)
                sliderAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
            }
        }

        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(
            LinearLayoutManager(this)
        ) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call load more items to get more data
                getMoreItems()
            }
        })

        binding.swipeToRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE)
        binding.swipeToRefresh.setColorSchemeColors(Color.BLACK)


        binding.swipeToRefresh.setOnRefreshListener {
            observeUI()
            binding.swipeToRefresh.isRefreshing = false
        }

        movieViewModel.fetchUpcoming(API_KEY)
        movieViewModel.fetchNowPlaying(API_KEY)
        observeUI()
        observeSlider()
    }

    private fun observeUI() {
        movieViewModel.upcomingMovies.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    val data = it.data!!.movies
                    movieAdapter.submitList(data!!)
                }
                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeSlider() {
        movieViewModel.movieNowPlaying.observe(this) {
            when(it) {
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    val data = it.data!!.movies
                    sliderAdapter.submitList(data!!)
                }
                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun clickOnItem(data: Movies, card: View) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, data)
        startActivity(intent)
    }

    fun getMoreItems() {
        isLoading = false
        movieAdapter.addData(list)
    }
}