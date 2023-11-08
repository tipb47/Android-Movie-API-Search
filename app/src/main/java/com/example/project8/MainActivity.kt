package com.example.project8

import com.example.project8.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //search button
        binding.btnSearch.setOnClickListener {
            val title = binding.etMovieTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                searchMovie(title)
            } else {
                Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //inflate menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //on feedback button clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_feedback -> {
                // email info
                val developerEmail = "ttbrowne@iu.edu"
                val subject = "Feedback"

                // set email intents
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "message/rfc822"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(developerEmail))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

                // start the email composer
                startActivity(Intent.createChooser(emailIntent, "Send Email"))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun searchMovie(title: String) {
        //call movie given title, return details
        RetrofitClient.apiService.getMovieDetails(apiKey = "c99a6d1b", title = title).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse?.response == "True") {
                        displayMovieData(movieResponse)
                    } else {
                        showToast(movieResponse?.error ?: "An error occurred")
                    }
                } else {
                    showToast("Error ${response.code()}: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                showToast("Error: ${t.localizedMessage}")
            }
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayMovieData(movie: MovieResponse) {
        //set movie details on xml
        with (binding) {
            movieDetailsContainer.visibility = View.VISIBLE
            binding.movieDetailsLayout.tvMovieTitle.text = movie.title ?: "N/A"
            binding.movieDetailsLayout.tvMovieYear.text = "Year: ${movie.year ?: "N/A"}"
            binding.movieDetailsLayout.tvMovieRating.text = "Rating: ${movie.rated ?: "N/A"}"
            binding.movieDetailsLayout.tvMovieRuntime.text = "Runtime: ${movie.runtime ?: "N/A"}"
            binding.movieDetailsLayout.tvMovieGenre.text = "Genre: ${movie.genre ?: "N/A"}"
            binding.movieDetailsLayout.tvMovieImdbRating.text = "IMDb Rating: ${movie.imdbRating ?: "N/A"}"

            Glide.with(this@MainActivity)
                .load(movie.poster)
                .placeholder(R.drawable.ic_feedback)
                .into(binding.movieDetailsLayout.tvMoviePoster)
        }
        //visit imdb website button
        binding.btnImdb.setOnClickListener {
            movie.imdbID?.let { imdbId ->
                val imdbIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/$imdbId"))
                startActivity(imdbIntent)
            }
        }
        //share imdb button
        binding.btnShare.setOnClickListener {
            movie.title?.let { title ->
                movie.imdbID?.let { imdbId ->
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Check out this movie: $title - https://www.imdb.com/title/$imdbId")
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            }
        }
    }
}
