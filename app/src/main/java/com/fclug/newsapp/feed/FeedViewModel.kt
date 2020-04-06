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
    val articles: LiveData<PagedList<Article>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(true)
            .build()
        articles = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }
}