package com.fclug.newsapp.data.repository

import com.fclug.newsapp.data.NewsApi
import com.fclug.newsapp.model.ArticlesResponse
import io.reactivex.Single

interface Repository {

    fun getTopHeadlines(
        category: String = NewsApi.DEFAULT_CATEGORY,
        country: String,
        pageSize: Int,
        page: Int
    ): Single<ArticlesResponse>
}