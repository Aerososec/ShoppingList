package com.example.shoppinglist.presentetion.shopItemScreen

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.repository.ShopItemRepositoryImpl
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.usecase.CreateShopItemUseCase
import com.example.shoppinglist.domain.usecase.GetItemByIdUseCase
import com.example.shoppinglist.domain.usecase.UpdateShopItemUseCase
import kotlin.text.Regex


class ViewModelSI : ViewModel() {
    private val repository = ShopItemRepositoryImpl()
    private val createShopItemUseCase = CreateShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)
    private val getItemByIdUseCase = GetItemByIdUseCase(repository)

    var id = 500 // TODO заменить на авто генерацию id

    fun createShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInputName(name) && validateInputCount(count)){
            val item = ShopItem(id++, name, count, true)
            createShopItemUseCase.execute(item)
        }

    }

    fun updateShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInputName(name) && validateInputCount(count)){
            val item = ShopItem(id++, name, count, true)
            updateShopItemUseCase.execute(item)
        }

    }

    fun getItemById(id: Int) : ShopItem {
        return getItemByIdUseCase.execute(id)
    }

    private fun parseName(inputName: String?) : String{
        return inputName?.trim()?.replace(Regex("\\s+"), " ") ?: ""
    }

    private fun parseCount(inputCount: String?) : Int{
        return try {
            inputCount?.trim()?.toInt() ?: 0
        }
        catch (e: Exception){
            0
        }
    }

    private fun validateInputName(name: String) : Boolean{
        val result = name.isNotBlank() // TODO invalid input error
        return result
    }

    private fun validateInputCount(count : Int) : Boolean{
        val result = count > 0 // TODO invalid input error
        return result
    }
}