package com.fangli.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections.emptyList

class MoviesRemoteSource: MovieSource {

    override fun getPopularMovies(callback: MovieSource.MovieSourceCallback) {
        MoviesApi.getInstance()?.getPopularMovies()?.enqueue(object: Callback<PopularMovieResponse>{
            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PopularMovieResponse>, response: Response<PopularMovieResponse>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        callback.OnMoviesReady(it.results)
                    }

                }
            }

        })
    }

    override fun getTopRatedMovies(callback: MovieSource.MovieSourceCallback)  {
        MoviesApi.getInstance()?.getTopRatedMovies()?.enqueue(object: Callback<TopRatedMovieResponse>{
            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<TopRatedMovieResponse>, response: Response<TopRatedMovieResponse>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        callback.OnMoviesReady(it.results)
                    }

                }
            }

        })
    }
}