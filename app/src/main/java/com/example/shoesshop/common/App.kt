package com.example.shoesshop.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver

class App : Application(), LifecycleObserver {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: App

        private lateinit var instance: Application

        fun getContext(): Context {
            return instance.applicationContext
        }
    }


    init {
        app = this
    }
}