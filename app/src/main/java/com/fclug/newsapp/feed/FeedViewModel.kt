package com.fclug.newsapp.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fclug.newsapp.data.datasource.NewsDataSourceFactory
import com.fclug.newsapp.data.repository.Repository
import com.fclug.newsapp.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedViewModel(repository: Repository): ViewModel(), KoinComponent {

    private val dataSourceFactory: NewsDataSourceFactory by inject()
    private val _articles = MutableLiveData<LiveData<PagedList<Article>>>()
    val articles: LiveData<LiveData<PagedList<Article>>>
        get() = _articles

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(true)
            .build()
        _articles.value = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    fun updateCountry(country: String) {
        dataSourceFactory.updateCountry(country)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(true)
            .build()
        _articles.value = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }
}