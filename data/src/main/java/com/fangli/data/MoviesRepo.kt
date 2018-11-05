package com.fangli.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fangli.data.util.DbWorkerThread

class MoviesRepo(context: Context) {

    private val dbWorker = DbWorkerThread("dbWorker")

    private val moviesDb: MoviesDatabase = MoviesDatabase.getInstance(context)!!
    private val moviesRemote: MoviesRemoteSource = MoviesRemoteSource()

    private var popularMoviesList: LiveData<List<Movie>>? = null
    private var highestRatedMoviesList: LiveData<List<Movie>>? = null

    private var LAST_CALL_POPULAR = 0L
    private var LAST_CALL_TOP = 0L
    init {
        dbWorker.start()
    }

    //First we try yo get the list from the api while retrieving the list from the database
    fun getPopularMovies(force: Boolean): LiveData<List<Movie>>{
        loadPopularMovies(force)
        popularMoviesList?.let {
            return it
        }
        popularMoviesList = moviesDb.moviesDao().getPopularMovies()
        return popularMoviesList as LiveData<List<Movie>>
    }

    fun getTopRatedMovies(force: Boolean): LiveData<List<Movie>>{
        loadTopRatedMovies(force)
        highestRatedMoviesList?.let {
            return it
        }
        highestRatedMoviesList = moviesDb.moviesDao().getTopRatedMovies()
        return highestRatedMoviesList as LiveData<List<Movie>>
    }

    private fun loadTopRatedMovies(force: Boolean) {
        //we use a simple way to avoid calling the api too many times (only one per minute)
        if (!force && LAST_CALL_TOP > 0L && (System.currentTimeMillis() - LAST_CALL_TOP) < 60000){
            return
        }
        //we retrieve the list and update the database.
        //This will also update any observer
        moviesRemote.getTopRatedMovies(object: MovieSource.MovieSourceCallback {
            override fun OnMoviesReady(movies: List<Movie>) {
                dbWorker.postTask(Runnable {
                    if (movies.isNotEmpty()){
                        moviesDb.moviesDao().deleteTopRatedMovies()
                        moviesDb.moviesDao().insertMovies(movies)
                        LAST_CALL_TOP = System.currentTimeMillis()
                    }
                })
            }
        })
    }

    private fun loadPopularMovies(force: Boolean) {
        if (force  && LAST_CALL_POPULAR > 0L && (System.currentTimeMillis() - LAST_CALL_TOP) < 60000){
            return
        }
        moviesRemote.getPopularMovies(object: MovieSource.MovieSourceCallback {
            override fun OnMoviesReady(movies: List<Movie>) {
                dbWorker.postTask(Runnable {
                    if (movies.isNotEmpty()){
                        moviesDb.moviesDao().deletePopularMovies()
                        moviesDb.moviesDao().insertMovies(movies)
                        LAST_CALL_POPULAR = System.currentTimeMillis()
                    }
                })
            }
        })
    }

    fun getMovie(id: Int): LiveData<Movie>{
        return moviesDb.moviesDao().getMovie(id)
    }
}