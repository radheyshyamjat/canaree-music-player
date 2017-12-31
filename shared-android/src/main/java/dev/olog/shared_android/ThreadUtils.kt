package dev.olog.shared_android

import android.os.Looper

private fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun assertMainThread() {
    if (BuildConfig.DEBUG) {
        val isMainThread = isMainThread()
        if (!isMainThread) {
            throw AssertionError("not on main thread " + Thread.currentThread())
        }
    }
}

fun assertBackgroundThread() {
    if (BuildConfig.DEBUG) {
        val isBackgroundThread = !isMainThread()
        if (!isBackgroundThread) {
            throw AssertionError("not on worker thread " + Thread.currentThread())
        }
    }
}