package com.example.mylist.model

interface OnListClickListener {
    fun onClick(id: Int)
    fun onLongClick(position: Int, product: Product)
}