package com.example.shoesshop.common

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LifecycleObserver

class App : Application(), LifecycleObserver {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: App

    }

    init {
        app = this
    }
}