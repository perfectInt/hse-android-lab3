package io.sultanov.newsfetching

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity)

        val titleTextView: TextView = findViewById(R.id.textViewTitle)
        titleTextView.text = intent.getStringExtra("newsTitle")
        val linkTextView: TextView = findViewById(R.id.textViewLink)
        linkTextView.text = intent.getStringExtra("newsLink")
        val contentTextView: TextView = findViewById(R.id.textViewDescription)
        contentTextView.text = intent.getStringExtra("newsContent")
        val newsCountryIntent = intent.getStringArrayExtra("newsCountry")
        if (newsCountryIntent != null) {
            val countryTextView: TextView = findViewById(R.id.textViewCountry)
            val countries = TextUtils.join(", ", newsCountryIntent)
            countryTextView.text = countries
        }
        val newsCategoryIntent = intent.getStringArrayExtra("newsCategory")
        if (newsCategoryIntent != null) {
            val categoryTextView: TextView = findViewById(R.id.textViewCategory)
            val categories = TextUtils.join(", ", newsCategoryIntent)
            categoryTextView.text = categories
        }
        val newsAuthorsIntent = intent.getStringArrayExtra("newsAuthors")
        if (newsAuthorsIntent != null) {
            val authorTextView: TextView = findViewById(R.id.textViewAuthor)
            val authors = TextUtils.join(", ", newsAuthorsIntent)
            authorTextView.text = authors
        }
        val dateTextView: TextView = findViewById(R.id.textViewDate)
        dateTextView.text = intent.getStringExtra("newsDate")
    }
}