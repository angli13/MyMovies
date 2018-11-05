package com.fangli.data

import androidx.lifecycle.LiveData

interface MovieSource {
    fun getPopularMovies(callback: MovieSourceCallback)
    fun getTopRatedMovies(callback: MovieSourceCallback)

    interface MovieSourceCallback{
        fun OnMoviesReady(movies: List<Movie>)
    }
}

