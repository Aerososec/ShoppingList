package com.example.shoppinglist.presentetion.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglist.R
import com.example.shoppinglist.presentetion.shopItemScreen.ViewModelSI
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var textInputCount : TextInputLayout
    private lateinit var textInputName : TextInputLayout
    private lateinit var inputName : TextInputEditText
    private lateinit var inputCount : TextInputEditText
    private lateinit var saveBtn : Button
    private lateinit var screenMode : String
    private var itemId = -1

    private val viewModel : ViewModelSI by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        parseIntent()
        val mode = intent.getStringExtra(ACTIVITY_MODE)
        initViews()
        when(mode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        resetNameError()
        resetCountError()
        inputNameError()
        inputCountError()
        viewModel.liveDataCloseScreen.observe(this) {
            finish()
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
        viewModel.liveDataInputNameError.observe(this) {
            textInputName.error = inputError(it, INPUT_NAME_ERROR)
        }
    }

    private fun inputCountError(){
        viewModel.liveDataInputCountError.observe(this) {
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

    private fun parseIntent(){
        if (!intent.hasExtra(ACTIVITY_MODE)){
            throw RuntimeException("No parameter")
        }
        val mode = intent.getStringExtra(ACTIVITY_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT ){
            throw RuntimeException("Unknown mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT){
            if (!intent.hasExtra(SHOP_ITEM_ID)){
                throw RuntimeException("No parameter")
            }
            itemId = intent.getIntExtra(SHOP_ITEM_ID, -1)
        }
    }

    private fun initViews(){
        textInputCount = findViewById<TextInputLayout>(R.id.textInputCount)
        textInputName = findViewById<TextInputLayout>(R.id.textInputName)
        inputName = findViewById<TextInputEditText>(R.id.forItemName)
        inputCount = findViewById<TextInputEditText>(R.id.forItemCount)
        saveBtn = findViewById<Button>(R.id.saveItemInfoBtn)
    }

    private fun launchEditMode(){
        viewModel.getItemById(itemId)
        viewModel.liveDataShopItem.observe(this){
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

        fun modeAdd(context: Context) : Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ACTIVITY_MODE, MODE_ADD)
            return intent
        }

        fun modeEdit(context: Context, id: Int) : Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ACTIVITY_MODE, MODE_EDIT)
            intent.putExtra(SHOP_ITEM_ID, id)
            return intent
        }
    }
}