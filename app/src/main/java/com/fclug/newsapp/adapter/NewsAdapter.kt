package com.fclug.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fclug.newsapp.R
import com.fclug.newsapp.model.Article
import com.fclug.newsapp.util.loadImage
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsAdapter(private val onItemClick: (Article) -> Unit): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val movieList: MutableList<Article> = mutableListOf()

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article, onItemClick: (Article) -> Unit) {
            itemView.headline.text = article.title
            itemView.headlineImage.loadImage(article.urlToImage)

            itemView.setOnClickListener {
                onItemClick.invoke(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(movieList[position], onItemClick)
    }

    fun loadItems(articles: List<Article>) {
        movieList.addAll(articles)
        notifyDataSetChanged()
    }
}