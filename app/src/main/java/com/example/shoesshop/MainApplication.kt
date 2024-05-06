package com.example.shoesshop

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatDelegate
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.gmail.EmailController
import com.example.shoesshop.datastore.MySharedPreferences
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONException


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
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            // JSON here
        } catch (e2: JSONException) {

            e2.printStackTrace()
        } catch (e: Exception) {

            e.printStackTrace()
        }

        instance = this
//        initTimber()
        DataShared.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FetchDataFirebase.share.init()
        MySharedPreferences.shared.init(this)
        EmailController.shared.init("your@gmail.com", "yourpass")
    }

//    private fun initTimber() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
//    }




}