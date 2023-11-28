package com.example.mylist

import android.app.Application
import com.example.mylist.model.AppDataBase

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getDatabase(this)
    }
}