package com.fangli.mymovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bumptech.glide.Glide.init
import com.fangli.data.Movie
import com.fangli.data.MoviesRepo

class MoviesViewModel(application: Application): AndroidViewModel(application) {

    private val moviesRepo: MoviesRepo = MoviesRepo(application)
    val moviesList: MediatorLiveData<List<Movie>> = MediatorLiveData()
    var currentOder = Order.POPULAR

    init {
        moviesList.addSource(moviesRepo.getPopularMovies(false)) {result ->
            if (currentOder == Order.POPULAR){
                result?.let { moviesList.value = it }
            }
        }

        moviesList.addSource(moviesRepo.getTopRatedMovies(false)) {result ->
            if (currentOder == Order.TOP_RATED){
                result?.let { moviesList.value = it }
            }
        }
    }


    fun getPopularMovies(force: Boolean = false):LiveData<List<Movie>>{
        return moviesRepo.getPopularMovies(force)
    }

    fun getTopRatedMovies(force: Boolean = false):LiveData<List<Movie>>{
        return moviesRepo.getTopRatedMovies(force)
    }

    fun rearrangeMovies(order: Order) = when (order) {
        Order.POPULAR -> getPopularMovies().value?.let { moviesList.value = it }
        Order.TOP_RATED -> getTopRatedMovies().value?.let { moviesList.value = it }
    }.also { currentOder = order }

    fun reloadMovies(force: Boolean = false){
        when (currentOder){
            Order.POPULAR -> {
                moviesRepo.getPopularMovies(force)
            }
            Order.TOP_RATED -> {
                moviesRepo.getTopRatedMovies(force)
            }
        }
    }
}

