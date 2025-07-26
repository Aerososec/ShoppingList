package com.example.shoppinglist.presentetion.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shoppinglist.R
import com.example.shoppinglist.presentetion.shopItemScreen.ViewModelSI
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.getValue


class ShopItemFragment : Fragment() {
    private lateinit var textInputCount : TextInputLayout
    private lateinit var textInputName : TextInputLayout
    private lateinit var inputName : TextInputEditText
    private lateinit var inputCount : TextInputEditText
    private lateinit var saveBtn : Button
    private lateinit var screenMode : String
    private var itemId = -1

    private var modeScreen : String = "Screen Mode"
    private var shopItemID : Int = -1

    private val viewModel : ViewModelSI by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mode = modeScreen
        initViews(view)
        when(mode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        resetNameError()
        resetCountError()
        inputNameError()
        inputCountError()
        viewModel.liveDataCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun resetNameError(){
        inputName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun resetCountError(){
        inputCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun inputNameError(){
        viewModel.liveDataInputNameError.observe(viewLifecycleOwner) {
            textInputName.error = inputError(it, INPUT_NAME_ERROR)
        }
    }

    private fun inputCountError(){
        viewModel.liveDataInputCountError.observe(viewLifecycleOwner) {
            textInputCount.error = inputError(it, INPUT_COUNT_ERROR)
        }
    }

    private fun inputError(error: Boolean, message: String) : String?{
        return if(error){
            message
        } else{
            null
        }
    }

    private fun parseParams(){
        if (!requireArguments().containsKey(ACTIVITY_MODE)){
            throw RuntimeException("No parameter")
        }
        modeScreen = requireArguments().getString(ACTIVITY_MODE, "Unknown mode")
        if (modeScreen != MODE_ADD && modeScreen != MODE_EDIT ){
            throw RuntimeException("Unknown mode")
        }
        screenMode = modeScreen
        if (modeScreen == MODE_EDIT){
            shopItemID = requireArguments().getInt(SHOP_ITEM_ID, -1)
            if (shopItemID == -1){
                throw RuntimeException("No id")
            }
            itemId = shopItemID
        }
    }

    private fun initViews(view: View){
        textInputCount = view.findViewById<TextInputLayout>(R.id.textInputCount)
        textInputName = view.findViewById<TextInputLayout>(R.id.textInputName)
        inputName = view.findViewById<TextInputEditText>(R.id.forItemName)
        inputCount = view.findViewById<TextInputEditText>(R.id.forItemCount)
        saveBtn = view.findViewById<Button>(R.id.saveItemInfoBtn)
    }

    private fun launchEditMode(){
        viewModel.getItemById(itemId)
        viewModel.liveDataShopItem.observe(viewLifecycleOwner){
            inputName.setText(it.name)
            inputCount.setText(it.count.toString())
        }
        saveBtn.setOnClickListener {
            viewModel.updateShopItem(inputName.text.toString(), inputCount.text.toString())
        }
    }

    private fun launchAddMode(){
        saveBtn.setOnClickListener {
            viewModel.createShopItem(inputName.text.toString(), inputCount.text.toString())
        }
    }

    companion object{
        private const val ACTIVITY_MODE = "activity_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_app"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val INPUT_NAME_ERROR = "Input Name Error"
        private const val INPUT_COUNT_ERROR = "Input Count Error"

        fun newInstanceAddMode() : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ACTIVITY_MODE, MODE_ADD)
                }
            }
        }
        fun newInstanceEditMode(shopItemID: Int) : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ACTIVITY_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemID)
                }
            }
        }
    }



}