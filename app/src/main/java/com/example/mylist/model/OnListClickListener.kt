package com.example.mylist.model

interface OnListClickListener {
    fun onClick(id: Int, type: String)
    fun onLongClick(position: Int, product: Product)
}