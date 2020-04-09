package com.fclug.newsapp.data

import com.fclug.newsapp.model.ArticlesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object {
        const val URL = "http://newsapi.org/v2/"
        const val DEFAULT_COUNTRY = "br"
        const val DEFAULT_CATEGORY = "general"
    }

    @GET("top-headlines")
    fun topHeadlines(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Single<ArticlesResponse>
}