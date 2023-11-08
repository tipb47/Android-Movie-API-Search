package com.example.project8.model

import com.example.project8.MovieSearchItem
import com.google.gson.annotations.SerializedName
/**
 * response from the OMDb API when searching for specific movie.
 */
data class MovieSearchItem (
    @SerializedName("Title")
    val title: String?,

    @SerializedName("Year")
    val year: String?,

    @SerializedName("Ratings")
    val ratings: String?,

    @SerializedName("Runtime")
    val runtime: String?,

    @SerializedName("Genre")
    val genre: String?,

    @SerializedName("imdbID")
    val imdbID: String?,

    @SerializedName("Type")
    val type: String?,

    @SerializedName("Poster")
    val poster: String?
)
