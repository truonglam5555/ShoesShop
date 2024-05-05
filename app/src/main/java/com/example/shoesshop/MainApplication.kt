package com.example.shoesshop

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.datastore.MySharedPreferences
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication? {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
//        initTimber()
        DataShared.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FetchDataFirebase.share.init()
        MySharedPreferences.shared.init(this)
    }

//    private fun initTimber() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
//    }




}