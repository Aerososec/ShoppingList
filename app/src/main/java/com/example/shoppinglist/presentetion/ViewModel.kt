package com.example.shoppinglist.presentetion

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.repository.ShopItemRepositoryImpl
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.usecase.CreateShopItemUseCase
import com.example.shoppinglist.domain.usecase.DeleteShopItemUseCase
import com.example.shoppinglist.domain.usecase.GetItemByIdUseCase
import com.example.shoppinglist.domain.usecase.GetShopListUseCase
import com.example.shoppinglist.domain.usecase.UpdateShopItemUseCase

class ViewModel : ViewModel() {
    private val repository = ShopItemRepositoryImpl()

    private val createShopItemUseCase = CreateShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getItemByIdUseCase = GetItemByIdUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)

    val shopListLD = getShopListUseCase.execute()

    fun createShopItem(item: ShopItem){
        createShopItemUseCase.execute(item)
    }

    fun deleteShopItem(id: Int){
        deleteShopItemUseCase.execute(id)
    }

    fun getItemById(id: Int) : ShopItem{
        return getItemByIdUseCase.execute(id)
    }

    fun updateShopItem(newItem: ShopItem){
        updateShopItemUseCase.execute(newItem)
    }

    fun changeEnableState(item: ShopItem){
        val newItem = item.copy(enable = !item.enable)
        updateShopItem(newItem)
    }

}