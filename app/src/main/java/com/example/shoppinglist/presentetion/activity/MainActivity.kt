package com.example.shoppinglist.presentetion.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentetion.activity.ShopItemActivity.Companion.modeAdd
import com.example.shoppinglist.presentetion.activity.ShopItemActivity.Companion.modeEdit
import com.example.shoppinglist.presentetion.fragment.ShopItemFragment
import com.example.shoppinglist.presentetion.mainScreen.ShopListAdapter
import com.example.shoppinglist.presentetion.mainScreen.ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var shopListAdapter: ShopListAdapter
    private val viewModel : ViewModel by viewModels()
    private var shopItemFragmentLand : FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        shopItemFragmentLand = findViewById<FragmentContainerView>(R.id.forLand)
        setUpRecyclerView()
        viewModel.shopListLD.observe(this) {
            shopListAdapter.submitList(it)
        }

        val addButton = findViewById<FloatingActionButton>(R.id.addItemBtn)
        addButton.setOnClickListener {
            if (shopItemFragmentLand == null) {
                val intent = modeAdd(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddMode()
                launchFragment(fragment)
            }
        }
    }

    private fun launchFragment(fragment : Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.forLand, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpRecyclerView(){
        shopListAdapter = ShopListAdapter()
        val shopListRV = findViewById<RecyclerView>(R.id.forItems)
        shopListRV.layoutManager = LinearLayoutManager(this)
        shopListRV.adapter = shopListAdapter
        shopItemLongClick()
        shopItemClick()
        swipeRemoval(shopListRV)
    }

    private fun shopItemLongClick(){
        shopListAdapter.shopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }

    private fun shopItemClick(){
        shopListAdapter.shopItemClick = {
            if (shopItemFragmentLand == null){
                val intent = modeEdit(this, it.id)
                startActivity(intent)
            }
            else{
                val fragment = ShopItemFragment.newInstanceEditMode(it.id)
                launchFragment(fragment)
            }
        }
    }

    private fun swipeRemoval(shopListRV: RecyclerView){
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(shopListRV)
    }
}