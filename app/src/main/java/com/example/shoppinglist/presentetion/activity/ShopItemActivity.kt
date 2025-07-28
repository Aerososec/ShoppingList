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
import com.example.shoppinglist.presentetion.fragment.ShopItemFragment
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
        if (savedInstanceState == null)
            when(mode){
                MODE_EDIT -> startFragment(launchEditMode(itemId))
                MODE_ADD -> startFragment(launchAddMode())
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

    private fun startFragment(fragment: ShopItemFragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }

    private fun launchAddMode() : ShopItemFragment{
        return ShopItemFragment.newInstanceAddMode()
    }

    private fun launchEditMode(shopItemId : Int) : ShopItemFragment{
        return ShopItemFragment.newInstanceEditMode(shopItemId)
    }

    companion object{
        private const val ACTIVITY_MODE = "activity_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_app"
        private const val SHOP_ITEM_ID = "shop_item_id"

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