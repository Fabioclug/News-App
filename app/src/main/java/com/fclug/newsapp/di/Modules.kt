package com.fclug.newsapp.di

import com.fclug.newsapp.data.datasource.NewsDataSource
import com.fclug.newsapp.data.datasource.NewsDataSourceFactory
import com.fclug.newsapp.data.repository.NewsRepository
import com.fclug.newsapp.data.repository.Repository
import com.fclug.newsapp.details.DetailsViewModel
import com.fclug.newsapp.feed.FeedViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel { FeedViewModel(get()) }
    single<Repository> { NewsRepository() }
    viewModel { DetailsViewModel() }
    factory { NewsDataSource() }
    single { NewsDataSourceFactory() }
}