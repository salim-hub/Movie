package com.ecem.movieapp.data

data class UpcomingMovies(
    val dates: Dates,
    val page: Int,
    val resultModels: List<ResultModel>,
    val total_pages: Int,
    val total_results: Int
)