package com.fangli.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
open class Movie: Serializable {
    var adult: Boolean? = false
    var backdrop_path: String? = ""
    @Ignore
    var genre_ids: List<Int>? = listOf()
    var id: Int? = 0
    @PrimaryKey(autoGenerate = true)
    var local_id: Int? = null
    var original_language: String? = ""
    var original_title: String? = ""
    var overview: String? = ""
    var popularity: Double? = 0.0
    var poster_path: String? = ""
    var release_date: String? = ""
    var title: String? = ""
    var video: Boolean? = false
    var vote_average: Double? = 0.0
    var vote_count: Int? = 0
    var type: String? = ""
    var favorite: Boolean? = false

    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }
}

//We are going to use different version of movies for each endpoint response, so that
//the retrofit use a pre-built version of the movie

//Thi type will save the movie as popular
class PopularMovie: Movie() {

    init {
        type = Movie.POPULAR
    }

}

//This type will save the movie as a top rated movie in the database
class TopRatedMovie: Movie() {
    init {
        type = Movie.TOP_RATED
    }
}

fun Movie.getThumbnailUrl(): String {
    return "http://image.tmdb.org/t/p/w185$backdrop_path"
}

fun Movie.getFullImageUrl(): String {
    return "http://image.tmdb.org/t/p/original$backdrop_path"
}

fun Movie.getThumbnailPosterUrl(): String {
    return "http://image.tmdb.org/t/p/w185$poster_path"
}

fun Movie.getFullImagePosterUrl(): String {
    return "http://image.tmdb.org/t/p/original$poster_path"
}