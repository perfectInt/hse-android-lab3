package io.sultanov.newsfetching

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val apiKey: String = "pub_340469d6333404c292102362c229ad4b54efe";

    private val adapter = RecyclerAdapter()

    private val newsApiService: NewsApiService by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsdata.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NewsApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextKeyword: EditText = findViewById(R.id.editTextKeyword)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNews)
        val layoutManager = GridLayoutManager(this, 2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val buttonSearch: Button = findViewById(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            Log.d("Main Activity", "button has been pressed")
            val keyword = editTextKeyword.text.toString()
            searchNews(keyword)
        }
    }

    private fun searchNews(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<NewsResponse> = newsApiService.getNews(keyword, apiKey)

                Log.d("Main Activity", "$response")

                if (response.isSuccessful) {
                    Log.d("Main Activity", "response is successful: ${response.body()}")
                    val jsonObject = response.body()?.results
                    Log.d("Main Activity", "$jsonObject")

                    withContext(Dispatchers.Main) {
                        if (jsonObject != null) {
                            Log.d("adapter", jsonObject.toString())
                            adapter.updateItems(jsonObject)
                        }
                    }
                } else {
                    Log.e("Main Activity", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: $e")
            }
        }
    }
}