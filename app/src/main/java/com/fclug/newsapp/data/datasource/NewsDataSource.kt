package com.fclug.newsapp.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.fclug.newsapp.SettingsManager
import com.fclug.newsapp.data.repository.Repository
import com.fclug.newsapp.model.Article
import com.fclug.newsapp.util.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit

const val PAGE_SIZE = 15
const val RETRIES = 5L
const val RETRY_DELAY = 5L

class NewsDataSource: PageKeyedDataSource<Int, Article>(), KoinComponent {

    private val repository: Repository by inject()
    private val settingsManager: SettingsManager by inject()
    private var totalPages = 0
    private var disposables = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        val currentPage = 1
        loadNews(currentPage) {articles, totalResults ->
            Log.d(TAG, "Total articles: $totalResults")
            totalPages = totalResults / PAGE_SIZE
            val remainder = totalResults % PAGE_SIZE
            if(remainder != 0)
                totalPages++
            callback.onResult(articles, null, currentPage + 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        val currentPage = params.key
        if(currentPage <= totalPages) {
            loadNews(currentPage) {articles, _ ->
                callback.onResult(articles, currentPage + 1)
            }
        }
    }

    private fun loadNews(page: Int, callback: (List<Article>, Int) -> Unit) {
        Log.d(TAG, "Current page: $page")
        disposables.add(repository.getTopHeadlines(page = page, pageSize = PAGE_SIZE,
                country = settingsManager.getCountryOption())
            .subscribeOn(Schedulers.io())
            .retryWhen {
                it.take(RETRIES).delay(RETRY_DELAY, TimeUnit.SECONDS)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                callback(response.articles, response.totalResults)
            }, {error ->
                //TODO treat errors
                Log.d(TAG, "Failed to load news", error)
            })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}

    fun finalize() {
        disposables.dispose()
    }
}