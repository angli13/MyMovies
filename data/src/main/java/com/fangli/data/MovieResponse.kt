package com.fangli.data


open class PopularMovieResponse {
    var page: Int = 0
    var results: List<PopularMovie> = listOf()
    var total_pages: Int = 0
    var total_results: Int = 0
}

open class TopRatedMovieResponse {
    var page: Int = 0
    var results: List<TopRatedMovie> = listOf()
    var total_pages: Int = 0
    var total_results: Int = 0
}
