package com.fangli.data

import retrofit2.Call
import retrofit2.http.GET


interface MoviesApiInterface {
    companion object {
        const val API_KEY = "332df2c76c8b46364b0483be7ba4078c"
    }

    @GET("popular?api_key=$API_KEY")
    fun getPopularMovies(): Call<PopularMovieResponse>

    @GET("top_rated?api_key=$API_KEY")
    fun getTopRatedMovies(): Call<TopRatedMovieResponse>
}