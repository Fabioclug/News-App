package com.fclug.newsapp.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fclug.newsapp.data.datasource.NewsDataSourceFactory
import com.fclug.newsapp.model.Article
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedViewModel: ViewModel(), KoinComponent {

    private val dataSourceFactory: NewsDataSourceFactory by inject()
    private var _articles = MutableLiveData<LiveData<PagedList<Article>>>()
    val articles: LiveData<LiveData<PagedList<Article>>>
        get() = _articles

    init {
        loadArticlesList()
    }

    fun loadArticlesList() {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(true)
            .build()
        _articles.value = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }
}