package com.carpathianapiary.beekeepernotes.utils

import android.util.Log
import com.carpathianapiary.beekeepernotes.BuildConfig

object LogUtils {

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun d(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message + e.message)
        }
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    fun w(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message + e.message)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun e(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message + e.message)
        }
    }

    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun v(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message + e.message)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun i(tag: String, message: String, e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message + e.message)
        }
    }

    fun log(tag: String, s: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, s)
        }
    }
}
