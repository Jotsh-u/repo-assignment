package com.myrepo

import android.app.Application

class MyApp : Application() {
    companion object{
        lateinit var INSTANCE : MyApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}