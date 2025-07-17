package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

class CreateShopItemUseCase(private val repository: ShopItemRepository) {
    fun execute(item: ShopItem){
        repository.createItem(item)
    }
}