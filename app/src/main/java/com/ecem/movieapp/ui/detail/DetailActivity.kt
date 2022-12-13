package com.ecem.movieapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ecem.movieapp.R
import com.ecem.movieapp.common.Constants.EXTRA_MOVIE
import com.ecem.movieapp.common.Constants.IMDB_URL
import com.ecem.movieapp.databinding.ActivityDetailBinding
import com.ecem.movieapp.data.model.Movies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movies::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_MOVIE)
        }

        binding.movieTitle.text = movie?.title
        binding.imdbRank.text = getString(R.string.rating_ten).format(movie?.voteAverage.toString())
        binding.movieDate.text = movie?.releaseDate
        binding.movieAbstract.text = movie?.overview

        val url = "https://image.tmdb.org/t/p/w500" + movie?.posterPath
        Glide.with(this)
            .load(url)
            .into(binding.movieImage)

        binding.imdbLogo.setOnClickListener {
            val imdbUrl = IMDB_URL
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl)))
        }
    }
}