package com.example.shoppinglist.presentetion.shopItemScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.repository.ShopItemRepositoryImpl
import com.example.shoppinglist.domain.models.ShopItem
import com.example.shoppinglist.domain.usecase.CreateShopItemUseCase
import com.example.shoppinglist.domain.usecase.GetItemByIdUseCase
import com.example.shoppinglist.domain.usecase.UpdateShopItemUseCase
import kotlin.text.Regex


class ViewModelSI : ViewModel() {
    private val repository = ShopItemRepositoryImpl
    private val createShopItemUseCase = CreateShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)
    private val getItemByIdUseCase = GetItemByIdUseCase(repository)

    var id = 500 // TODO заменить на авто генерацию id

    private val _liveDataInputNameError = MutableLiveData<Boolean>()
    val liveDataInputNameError: LiveData<Boolean>
        get() = _liveDataInputNameError

    private val _liveDataInputCountError = MutableLiveData<Boolean>()
    val liveDataInputCountError: LiveData<Boolean>
        get() = _liveDataInputCountError

    private val _liveDataShopItem = MutableLiveData<ShopItem>()
    val liveDataShopItem : LiveData<ShopItem>
        get() = _liveDataShopItem

    private val _liveDataCloseScreen = MutableLiveData<Unit>()
    val liveDataCloseScreen : LiveData<Unit>
        get() = _liveDataCloseScreen

    fun createShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInputName(name) && validateInputCount(count)){
            val item = ShopItem(id++, name, count, true)
            createShopItemUseCase.execute(item)
            finishWork()
        }
    }

    fun updateShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInputName(name) && validateInputCount(count)){
            _liveDataShopItem.value?.let {
                val item = it.copy(name = name, count = count)
                updateShopItemUseCase.execute(item)
                finishWork()
            }
        }
    }

    fun finishWork(){
        _liveDataCloseScreen.value = Unit
    }

    fun getItemById(id: Int) {
        val item = getItemByIdUseCase.execute(id)
        _liveDataShopItem.value = item
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
        val result = name.isNotBlank()
        inputNameError(result)
        return result
    }

    private fun inputNameError(result: Boolean){
        if (!result){
            _liveDataInputNameError.value = true
        }
    }

    fun resetInputNameError(){
        _liveDataInputNameError.value = false
    }

    private fun validateInputCount(count : Int) : Boolean{
        val result = count > 0
        inputCountError(result)
        return result
    }

    fun resetInputCountError(){
        _liveDataInputCountError.value = false
    }

    private fun inputCountError(result: Boolean){
        if (!result){
            _liveDataInputCountError.value = true
        }
    }
}