package com.example.watchlist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MovieDetails : AppCompatActivity() {

    // Lateinit holders for the views and data
    private lateinit var extras: Bundle
    private lateinit var posterImg: ImageView
    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var genreText: TextView
    private lateinit var descText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Set view handlers amd extra data
        posterImg = findViewById(R.id.MD_poster_img)
        titleText = findViewById(R.id.MD_title_text)
        dateText = findViewById(R.id.MD_date_text)
        genreText = findViewById(R.id.MD_genre_text)
        descText = findViewById(R.id.MD_desc_text)
        extras = intent.getBundleExtra("dataBundle")!!

        bindValues()
    }

    private fun bindValues(){
        Picasso.get().load(extras.getString("img_url")).into(posterImg)
        titleText.text = extras.getString("title")
        dateText.text = extras.getString("date")
        genreText.text = extras.getString("genres")
        descText.text = extras.getString("overview")
    }


}