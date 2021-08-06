package com.example.watchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieListAdapter(private val movieList: List<Movie>?, private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    // I'm not proud of this, but a completed app that I can update is better than an incomplete app that I can't

    val BASE_IMG_URL: String = "https://image.tmdb.org/t/p/"
    val DEFAULT_SIZE: String = "original"

    // Personal preference not to directly access the object
    private var moviesArray = movieList?.toTypedArray()

    inner class MovieListViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){
                var posterImage: ImageView
                var titleText: TextView
                var releaseDate: TextView
                var genreText: TextView

                init{
                    posterImage = itemView.findViewById(R.id.CV_poster_img)
                    titleText = itemView.findViewById(R.id.CV_title_text)
                    releaseDate = itemView.findViewById(R.id.CV_release_text)
                    genreText = itemView.findViewById(R.id.CV_genre_text)
                }
            }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder{
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_layout, parent, false)

        return MovieListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int){
        val sb = StringBuilder()

        sb.append(BASE_IMG_URL)
        sb.append(DEFAULT_SIZE)
        sb.append(moviesArray?.get(position)?.posterPath)

        Picasso.get().load(sb.toString()).into(holder.posterImage)
        holder.titleText.text = moviesArray?.get(position)?.title
        holder.releaseDate.text = moviesArray?.get(position)?.releaseDate
        holder.genreText.text = getGenreString(moviesArray?.get(position)?.genreIds)

        // I'm aware this is less than ideal, but I'm trying to get the app completed.
        holder.itemView.setOnClickListener{listener(movieList!![position])}
    }

    // Builds a formatted string for genre display.
    // This one breaks after 2 loops so the genre display doesn't get too big
    private fun getGenreString(genres: List<Int>?): String {
        val stringBuilder = StringBuilder()
        if (genres != null) {
            var i = 0
            for(item in genres){
                if(i < 2){
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
                    i++
                }
                else{
                    stringBuilder.append("See Desc.")
                    break
                }

            }
        }

        return stringBuilder.toString()

    }

    override fun getItemCount(): Int {
        if (movieList != null) {
            return movieList.size
        }
        else return -1
    }
}