package com.fclug.newsapp.data.datasource

import androidx.paging.DataSource
import com.fclug.newsapp.model.Article

class NewsDataSourceFactory(private val newsDataSource: NewsDataSource): DataSource.Factory<Int, Article>() {

    override fun create(): DataSource<Int, Article> {
        return newsDataSource
    }

    fun finalize() {
        newsDataSource.finalize()
    }
}