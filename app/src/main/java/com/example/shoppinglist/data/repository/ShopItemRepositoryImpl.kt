package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

class ShopItemRepositoryImpl : ShopItemRepository {
    private val shopList = mutableListOf<ShopItem>()
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    init {
        for (i in 0..100){
            val flag = if (i % 2 == 0) true else false
            shopList.add(ShopItem(id = i, name = i.toString(), count = i, flag))
        }
        updateLD()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        shopListLD.value = shopList.toList()
        return shopListLD
    }

    override fun updateItem(newItem: ShopItem) {
        val index = shopList.indexOfFirst { it.id == newItem.id }
        if(index != -1){
          shopList[index] = newItem.copy()
        }
        updateLD()
    }

    override fun createItem(item: ShopItem) {
        shopList.add(item)
        updateLD()
    }

    override fun getItemBuId(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw Exception("Non-existent id")
    }

    override fun deleteItem(id: Int) {
        shopList.remove(shopList.find { it.id == id })
        updateLD()
    }

    private fun updateLD(){
        shopListLD.value = shopList.toList()
    }
}