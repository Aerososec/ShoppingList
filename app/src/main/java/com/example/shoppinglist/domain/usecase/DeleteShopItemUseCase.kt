package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.repository.ShopItemRepository

class DeleteShopItemUseCase(private val repository: ShopItemRepository){
    fun execute(id: Int){
        repository.deleteItem(id)
    }
}