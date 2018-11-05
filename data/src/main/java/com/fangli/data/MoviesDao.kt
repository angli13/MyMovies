package com.fangli.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie WHERE type = '${Movie.POPULAR}'")
    fun getPopularMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE type = '${Movie.TOP_RATED}'")
    fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE local_id = :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Query("DELETE FROM movie WHERE type = '${Movie.POPULAR}'")
    fun deletePopularMovies()

    @Query("DELETE FROM movie WHERE type = '${Movie.TOP_RATED}'")
    fun deleteTopRatedMovies()


}