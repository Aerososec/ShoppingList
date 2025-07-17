package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

class UpdateShopItemUseCase(private val repository: ShopItemRepository) {
    fun execute(newItem: ShopItem){
        repository.updateItem(newItem)
    }
}