package com.fclug.newsapp.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclug.newsapp.data.repository.Repository
import com.fclug.newsapp.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FeedViewModel(repository: Repository): ViewModel() {

    private val articles: MutableLiveData<List<Article>> = MutableLiveData()
    private var disposable: Disposable = CompositeDisposable()

    init {
        disposable = repository.getTopHeadlines(page = 1, pageSize = 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                articles.value = response.articles
            }, {error ->
                Log.d("FeedViewModel", "Failed to load news", error)
            })
    }

    fun getArticles(): LiveData<List<Article>> {
        return articles
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}