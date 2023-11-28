package com.example.mylist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Product::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun product(): ProductDao

    companion object {

        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return if (INSTANCE == null) {

                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "my_list"
                    ).build()
                }
                INSTANCE as AppDataBase
            } else {
                INSTANCE as AppDataBase
            }
        }
    }

}