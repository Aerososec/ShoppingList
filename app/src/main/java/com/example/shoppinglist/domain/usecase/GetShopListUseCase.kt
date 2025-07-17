package com.example.shoppinglist.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.repository.ShopItemRepository

class GetShopListUseCase(private val repository: ShopItemRepository) {
    fun execute() : LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}