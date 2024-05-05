package com.example.shoesshop.datastore

import android.content.Context

class MySharedPreferences {
    private val TAG = this::class.simpleName
    private val APP_PREFERENCES = "APP_PREFERENCES"
    private lateinit var  mContext : Context
    companion object {
        val shared: MySharedPreferences = MySharedPreferences()
    }

    fun init(context: Context)
    {
        mContext = context
    }

    fun putStringValue(key: String, n: String) {
        val pref = mContext.getSharedPreferences(APP_PREFERENCES, 0)
        val editor = pref.edit()
        editor.putString(key, n)
        editor.apply()
    }

    fun pullStringValue(key: String) : String? {
        val pref = mContext.getSharedPreferences(APP_PREFERENCES, 0)
        return pref.getString(key, "")
    }
}