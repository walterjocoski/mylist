package com.example.mylist.model

import android.widget.TextView
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Insert
    fun insert(product: Product)

    @Query("SELECT * FROM Product")
    fun getRegisterById(): List<Product>

    @Delete
    fun delete(product: Product): Int

    @Update
    fun update(product: Product)

//    @Query("SELECT SUM(totalValue) FROM Product")
//    fun getTotalValue()
}