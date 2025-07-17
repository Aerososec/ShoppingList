package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

class GetItemByIdUseCase(private val repository: ShopItemRepository) {
    fun execute(id: Int) : ShopItem{
        return repository.getItemBuId(id)
    }
}