package com.example.project8

import com.google.gson.annotations.SerializedName

/**
 * response from the OMDb API when searching for movies.
 */
data class MovieSearchResponse(
    @SerializedName("Search")
    val search: List<MovieSearchItem>,

    @SerializedName("totalResults")
    val totalResults: String?,

    @SerializedName("Response")
    val response: String
)

