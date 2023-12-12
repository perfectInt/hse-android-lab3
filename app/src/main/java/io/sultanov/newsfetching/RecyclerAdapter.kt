package io.sultanov.newsfetching

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var newsList: ArrayList<NewsItem> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val description: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = newsList[position].title
        holder.description.text = newsList[position].description

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java)
            intent.putExtra("newsTitle", newsList[position].title)
            intent.putExtra("newsDate", newsList[position].pubDate)
            intent.putExtra("newsContent", newsList[position].content)
            intent.putExtra("newsCountry", newsList[position].country)
            intent.putExtra("newsLink", newsList[position].link)
            intent.putExtra("newsCategory", newsList[position].category)
            intent.putExtra("newsAuthors", newsList[position].creator)
            holder.itemView.context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemList: ArrayList<NewsItem>) {
        newsList = newItemList
        notifyDataSetChanged()
    }
}