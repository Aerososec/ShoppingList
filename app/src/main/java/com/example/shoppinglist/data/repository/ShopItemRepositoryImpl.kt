package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {
    private val shopList = mutableListOf<ShopItem>()
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    init {
        for (i in 0..10){
            val flag = if (i % 2 == 0) true else false
            shopList.add(ShopItem(id = i, name = i.toString(), count = i, flag))
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        updateLD()
        return shopListLD
    }

    override fun updateItem(newItem: ShopItem) {
        val index = shopList.indexOfFirst { it.id == newItem.id }
        if(index != -1){
          shopList[index] = newItem.copy()
        }
        else{
            throw RuntimeException("Unknown id")
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
        shopListLD.postValue(shopList.toList())
    }
}