package com.fangli.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.get
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import com.fangli.data.Movie
import com.fangli.data.getFullImageUrl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MainActivity : AppCompatActivity(), SettingsCallback, MovieSelectedListener {

    private lateinit var viewModel: MoviesViewModel
    private var adapter: MovieAdapter = MovieAdapter()
    private lateinit var settingsHelper: SettingsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settingsHelper = SettingsHelper(this)
        list.adapter = adapter
        adapter.listener = this
        list.layoutManager = GridLayoutManager(this, 3)
        swipe.setOnRefreshListener {
            viewModel.reloadMovies(true)
        }
        manageViewModel()
    }

    override fun OnMovieSelected(movie: Movie, position: Int, imageView: ImageView) {
        Router.openMovieDetail(this, movie, imageView)
    }

    private fun manageViewModel() {
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        settingsHelper.getSelectedOrder().apply {
            setOrder(this)
        }
        viewModel.moviesList.observe(this, observer)
    }

    private fun setOrder(order: Order) {
        viewModel.rearrangeMovies(order)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.order){
                settingsHelper.showOptionDialog(this, this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun OnOrderChanged(order: Order) {
        setOrder(order)
    }

    val observer: Observer<List<Movie>> = Observer {
        loadMovies(it)
    }

    private fun loadMovies(list: List<Movie>) {
        adapter.swapData(list)
        swipe.isRefreshing = false
    }

}
