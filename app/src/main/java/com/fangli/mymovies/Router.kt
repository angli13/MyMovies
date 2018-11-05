package com.fangli.mymovies

import android.app.Activity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat.getTransitionName
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.fangli.data.Movie


class Router {
    companion object {
        private val EXTRA_MOVIE= "movie"
        private val EXTRA_MOVIE_IMAGE_TRANSITION_NAME= "movie_transition"

        fun openMovieDetail(activity: AppCompatActivity, movie: Movie, imageView: ImageView){
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_MOVIE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView))

            ViewCompat.getTransitionName(imageView)?.let {
                val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, imageView, it).toBundle()
                activity.startActivity(intent, options)
                return
            }
            activity.startActivity(intent)
        }

        fun getMovie(intent: Intent): Movie {
            return intent.extras?.getSerializable(EXTRA_MOVIE) as Movie
        }

        fun getTransitionName(intent: Intent): String? {
            return intent.extras?.getString(EXTRA_MOVIE_IMAGE_TRANSITION_NAME)
        }
    }
}