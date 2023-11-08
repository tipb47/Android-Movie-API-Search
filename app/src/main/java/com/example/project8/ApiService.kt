package com.example.project8
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //other implementation call (search for movies)
//    @GET("/")
//    fun searchMoviesByTitle(
//        @Query("apikey") apiKey: String,
//        @Query("t") title: String,
//        @Query("type") type: String? = null,
//        @Query("y") year: String? = null,
//        @Query("r") format: String = "json",
//        @Query("page") page: Int? = null
//    ): Call<MovieSearchResponse>

    //implementation that gets specific movie details
    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String? = null,
        @Query("t") title: String? = null,
        @Query("type") type: String? = null,
        @Query("y") year: String? = null,
        @Query("plot") plot: String = "short",
        @Query("r") format: String = "json"
    ): Call<MovieResponse>
}
