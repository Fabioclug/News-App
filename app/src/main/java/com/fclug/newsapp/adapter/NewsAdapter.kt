package com.fclug.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fclug.newsapp.model.Article
import com.fclug.newsapp.databinding.NewsListItemBinding

class NewsAdapter(private val onItemClick: (Article) -> Unit):
    PagedListAdapter<Article, NewsAdapter.NewsViewHolder>(articleDiffCallback) {

    class NewsViewHolder(var binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onItemClick: (Article) -> Unit) {
            binding.article = article
            itemView.setOnClickListener {
                onItemClick.invoke(article)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsListItemBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClick) }
    }

    companion object {
        private val articleDiffCallback = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url && oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

        }
    }
}