package com.slf.latihanch4.data

import android.content.Context

object SharedPreferencesHelper {
    private const val PREF_NAME = "MyPrefs"
    private const val KEY_IS_LOGIN = "isLogin"

    fun setIsLogin(context: Context, isLogin: Boolean) {
        val editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(KEY_IS_LOGIN, isLogin)
        editor.apply()
    }

    fun getIsLogin(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGIN, false)
    }
}

