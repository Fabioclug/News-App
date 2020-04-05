package com.fclug.newsapp.data.repository

import com.fclug.newsapp.BuildConfig
import com.fclug.newsapp.data.NewsApi
import com.fclug.newsapp.model.ArticlesResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository: Repository {

    private val api: NewsApi = Retrofit.Builder()
        .baseUrl(NewsApi.URL)
        .client(OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // convert date automatically
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()))
        .build()
        .create(NewsApi::class.java)

    override fun getTopHeadlines(category: String, country: String, pageSize: Int, page: Int): Single<ArticlesResponse> {
        return api.topHeadlines(category, country, pageSize, page)
    }
}