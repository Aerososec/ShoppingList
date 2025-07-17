package com.example.shoppinglist.domain.models

data class ShopItem(
    val id: Int,
    var name: String,
    var count: Int,
    var enable: Boolean
)
