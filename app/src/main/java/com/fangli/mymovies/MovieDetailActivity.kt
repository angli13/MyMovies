package com.fangli.mymovies

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.fangli.data.Movie
import com.fangli.data.getFullImageUrl
import com.fangli.data.getThumbnailUrl
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), RequestListener<Drawable> {


    lateinit var mMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(main_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportPostponeEnterTransition()

        mMovie = Router.getMovie(intent)

        setUI()
    }

    private fun setUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Router.getTransitionName(intent)?.let {
                main_backdrop.transitionName = it
            }
        }
        Glide.with(this).load(mMovie.getThumbnailUrl()).listener(this).into(main_backdrop)
        title = mMovie.title
        year.text = mMovie.release_date
        rating.text = "${mMovie.vote_average}/10"
        language.text = mMovie.original_language
        summary.text = mMovie.overview
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        supportStartPostponedEnterTransition()
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        supportStartPostponedEnterTransition()
        //We load the full url after the image has been shown
        Handler().postDelayed( { Glide.with(main_backdrop).setDefaultRequestOptions(RequestOptions().apply {
            this.placeholder(main_backdrop.drawable)
        }).load(mMovie.getFullImageUrl()).into(main_backdrop) }, 200)
        return false
    }


}
