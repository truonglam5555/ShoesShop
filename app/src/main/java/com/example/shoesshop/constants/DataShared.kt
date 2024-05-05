package com.example.shoesshop.constants

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataShared {
    private var sharedPref: SharedPreferences? = null
    private const val PreferencesFile = "cemg_preferences"


    const val DEVICE_MODEL = "device_model"
    const val DEVICE_VERSION = "device_version"
    const val COUNTRY_CODE = "country_code"
    const val FCM_ACCESS_TOKEN = "fcm_access_token"
    const val TOKEN = "AUTH_TOKEN"

    //    Width, height
    const val WIDTH = "width_screen"
    const val HEIGHT = "height_screen"


    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(PreferencesFile, Context.MODE_PRIVATE)
        sharedPref?.edit()?.apply()
    }

    fun clearKey(keyName: String) {
        sharedPref?.edit {
            remove(keyName)
            apply()
        }
    }

    //saving list in Shared Preference
    fun setLists(list:ArrayList<String>){
        val gson = Gson()
        val json = gson.toJson(list)//converting list to Json
        setString("LIST",json)
    }

    //getting the list from shared preference
//    fun getList():ArrayList<String>{
//        val gson = Gson()
//        val json = sharedPref?.getString("LIST",null)
//        val type = object :TypeToken<ArrayList<String>>(){}.type//converting the json to list
//        return gson.fromJson(json,type)//returning the list
//    }

    fun getStringOfList(): String?{
        return sharedPref?.getString("LIST", "")
    }

    fun setWidth(value: Int){
        setInt(WIDTH, value)
    }

    fun getWidth(): Int?{
        return sharedPref?.getInt(WIDTH, 0)
    }

    fun setHeight(value: Int){
        setInt(HEIGHT, value)
    }

    fun getHeight(): Int?{
        return sharedPref?.getInt(HEIGHT, 0)
    }


    fun setFCMAccessToken(value: String) {
        setString(FCM_ACCESS_TOKEN, value)
    }

    fun getFCMAccessToken(): String? {
        return sharedPref?.getString(FCM_ACCESS_TOKEN, "null")
    }

    fun setToken(value: String) {
        setString(TOKEN, value)
    }

    fun getToken(): String? {
        return sharedPref?.getString(TOKEN, "")
    }

    fun setDeviceModel(value: String) {
        setString(DEVICE_MODEL, value)
    }

    fun getDeviceModel(): String? {
        return sharedPref?.getString(DEVICE_MODEL, null)
    }

    fun setDeviceVersion(value: String) {
        setString(DEVICE_VERSION, value)
    }

    fun getDeviceVersion(): String? {
        return sharedPref?.getString(DEVICE_VERSION, null)
    }

    fun setCountryCode(value: String) {
        setString(COUNTRY_CODE, value)
    }

    fun getCountryCode(): String? {
        return sharedPref?.getString(COUNTRY_CODE, "")
    }
    fun clearListHistory(){
        sharedPref?.edit{
            remove("LIST")
            apply()
        }
    }

    fun clearUser() {
        sharedPref?.edit {
            remove(TOKEN)
            clearListHistory()
            apply()
        }
    }

    fun clearDataEbookPlayList() {
        sharedPref?.edit {
//            remove(ACTIVE)
//            remove(ID_EBOOK_AUDIO)
//            remove(IS_STOP)
            apply()
        }
    }


    fun setMap(key: String, inputMap: Map<Any, Any>) {
        try {
            setString(key, Gson().toJson(inputMap))
        } catch (e: Exception) {
        }
    }

    fun getMap(key: String): Map<Any, Any> {
        return try {
            val type = object : TypeToken<HashMap<Any, Any>?>() {}.type
            Gson().fromJson(getString(key), type)
        } catch (e: Exception) {
            mapOf()
        }
    }

    @Synchronized
    fun setBoolean(@Key key: String?, value: Boolean?) {
        sharedPref?.edit()?.let { editor ->
            value?.let {
                editor.putBoolean(key, it)
                editor.apply()
            }
        }
    }

    @Synchronized
    fun getBoolean(@Key key: String?): Boolean {
        return sharedPref?.getBoolean(key, false) ?: false
    }

    @Synchronized
    fun setString(@Key key: String?, value: String?) {
        sharedPref?.edit()?.let { editor ->
            editor.putString(key, value)
            editor.apply()
        }
    }

    @Synchronized
    fun getString(@Key key: String?): String {
        return sharedPref?.getString(key, "") ?: ""
    }

    @Synchronized
    fun setInt(@Key key: String?, value: Int?) {
        sharedPref?.edit()?.let { editor ->
            editor.putInt(key, value ?: 0)
            editor.apply()
        }
    }

    @Synchronized
    fun getInt(@Key key: String?): Int {
        return sharedPref?.getInt(key, 0) ?: 0
    }


    internal annotation class Key
}