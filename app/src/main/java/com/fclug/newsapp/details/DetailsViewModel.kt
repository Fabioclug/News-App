package com.fclug.newsapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclug.newsapp.model.Article
import java.text.SimpleDateFormat
import java.util.*

class DetailsViewModel: ViewModel() {
    private val _currentArticle = MutableLiveData<Article>()

    val currentArticle: LiveData<Article>
        get() = _currentArticle

    fun updateArticle(article: Article) {
        _currentArticle.value = article
    }

    fun getPublishedDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        var date = Date()
        currentArticle.value?.let { date = it.publishedAt }
        return dateFormat.format(date)
    }
}