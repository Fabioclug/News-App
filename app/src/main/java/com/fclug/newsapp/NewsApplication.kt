package com.fclug.newsapp

import android.app.Application
import com.fclug.newsapp.di.appModule
import org.koin.android.ext.android.startKoin

class NewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}