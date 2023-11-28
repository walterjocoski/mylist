package com.example.mylist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val productName: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "unitValue") val unitValue: Double,
    @ColumnInfo(name = "totalValue") val totalValue: Double,
    @ColumnInfo(name = "type") val type: String
)
