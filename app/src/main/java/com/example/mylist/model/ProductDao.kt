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
    fun getRegister(): List<Product>

    @Delete
    fun delete(product: Product): Int

    @Update
    fun update(product: Product)

    @Query("SELECT SUM(totalValue) FROM Product")
    fun getTotalValue(): Double

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getProductById(id: Int): List<Product>
}