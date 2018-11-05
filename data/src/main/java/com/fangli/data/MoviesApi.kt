package com.fangli.data

import android.content.Context
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesApi {
    companion object {
        private var INSTANCE: MoviesApiInterface? = null

        fun getInstance(): MoviesApiInterface? {
            if (INSTANCE == null) {
                synchronized(MoviesDatabase::class) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("http://api.themoviedb.org/3/movie/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(MoviesApiInterface::class.java)
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}