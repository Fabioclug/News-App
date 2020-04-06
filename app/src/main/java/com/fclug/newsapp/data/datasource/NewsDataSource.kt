package com.fclug.newsapp.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.fclug.newsapp.data.repository.Repository
import com.fclug.newsapp.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NewsDataSource: PageKeyedDataSource<Int, Article>(), KoinComponent {

    companion object {
        const val PAGE_SIZE = 15
    }

    private val repository: Repository by inject()
    private var totalPages = 0
    private var disposables = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        val currentPage = 1
        loadNews(currentPage) {articles, totalResults ->
            Log.d("NewsDataSource", "Total articles: " + totalResults)
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
        disposables.add(repository.getTopHeadlines(page = page, pageSize = PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                callback(response.articles, response.totalResults)
            }, {error ->
                Log.d("NewsDataSource", "Failed to load news", error)
            })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}

    fun finalize() {
        disposables.dispose()
    }
}