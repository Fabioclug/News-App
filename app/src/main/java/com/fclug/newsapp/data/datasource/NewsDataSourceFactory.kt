package com.fclug.newsapp.data.datasource

import androidx.paging.DataSource
import com.fclug.newsapp.model.Article
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NewsDataSourceFactory: DataSource.Factory<Int, Article>(), KoinComponent {

    private val newsDataSource: NewsDataSource by inject()

    override fun create(): DataSource<Int, Article> {
        return newsDataSource
    }

    fun finalize() {
        newsDataSource.finalize()
    }
}