package com.example.watchlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Fragment to hold web data
class WebDisplayFragment : Fragment() {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesRecycler: RecyclerView
    private lateinit var moviesAdapter: MovieListAdapter
    private var moviesList: MovieListReply? = null
    private var configSettings: ConfigReply? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Create the view and inflate it
        val view: View = inflater.inflate(R.layout.fragment_web_display, container, false)

        //Layout manager for the RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // RecyclerView assignment
        moviesRecycler = view.findViewById(R.id.MovieListRecycler)

        // Quick and dirty way to ensure that the RecyclerView is never called with null data
        val moviesObserver = Observer<MovieListReply>{
                newList ->
            Log.i("MoviesObserver", "Movies Livedata updated -- ${newList.results.size} items retrieved")
            // Populate and access moviesList inside observer
            moviesList = newList
            moviesAdapter = MovieListAdapter(moviesList?.results){
                movie ->
                val intent = Intent(context, MovieDetails::class.java).apply {
                    val bundle = Bundle()
                    val sb = StringBuilder()

                    // Stringbuilder to create URL for Picasso
                    sb.append(moviesAdapter.BASE_IMG_URL)
                    sb.append(moviesAdapter.DEFAULT_SIZE)
                    sb.append(movie.posterPath)

                    // Assigning important data to Intent with a Bundle
                    bundle.putString("title", movie.title)
                    bundle.putString("date", movie.releaseDate)
                    bundle.putString("overview", movie.overview)
                    bundle.putString("genres", getGenreString(movie.genreIds))
                    bundle.putString("img_url", sb.toString())

                    putExtra("dataBundle", bundle)

                }

                // Start MovieDetails with selected item
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            moviesRecycler.layoutManager = layoutManager
            moviesRecycler.adapter = moviesAdapter
        }

        val configObserver = Observer<ConfigReply>{
                newConfig ->
            Log.i("ConfigObserver", "Config livedata updated")
            configSettings = newConfig
        }

        viewModel.getMovieList().observe(viewLifecycleOwner, moviesObserver)
        viewModel.getConfig().observe(viewLifecycleOwner, configObserver)

        Log.i("onCreateView", "${moviesList?.results?.size} movies in list ")
        return view
    }

    // This version of the function doesn't stop and completes the formatted genre string for use in the Intent
    private fun getGenreString(genres: List<Int>?): String{
        val stringBuilder = StringBuilder()
        if (genres != null) {
            for(item in genres){
                    when(item){
                        28 -> stringBuilder.append("Action\n")
                        12 -> stringBuilder.append("Adventure\n")
                        16 -> stringBuilder.append("Animation\n")
                        35 -> stringBuilder.append("Comedy\n")
                        80 -> stringBuilder.append("Crime\n")
                        99 -> stringBuilder.append("Documentary\n")
                        18 -> stringBuilder.append("Drama\n")
                        10751 -> stringBuilder.append("Family\n")
                        14 -> stringBuilder.append("Fantasy\n")
                        36 -> stringBuilder.append("History\n")
                        27 -> stringBuilder.append("Horror\n")
                        10402 -> stringBuilder.append("Music\n")
                        9648 -> stringBuilder.append("Mystery\n")
                        10749 -> stringBuilder.append("Romance\n")
                        878 -> stringBuilder.append("Science Fiction\n")
                        10770 -> stringBuilder.append("TV Movie\n")
                        53 -> stringBuilder.append("Thriller\n")
                        10752 -> stringBuilder.append("War\n")
                        37 -> stringBuilder.append("Western\n")

                        else -> stringBuilder.append("Invalid genre\n")

                    }
            }
        }

        return stringBuilder.toString()

    }
}