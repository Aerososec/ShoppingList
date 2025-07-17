package com.example.shoppinglist.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.models.ShopItem

interface ShopItemRepository {
    fun getShopList() : LiveData<List<ShopItem>>
    fun updateItem(newItem: ShopItem)
    fun createItem(item: ShopItem)
    fun getItemBuId(id: Int) : ShopItem
    fun deleteItem(id: Int)
}