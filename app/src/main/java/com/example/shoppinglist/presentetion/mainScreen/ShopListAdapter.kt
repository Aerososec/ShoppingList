package com.example.shoppinglist.presentetion.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.models.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(
    ShopItemDiffCallback()
) {

    var shopItemLongClick : ((ShopItem) -> Unit)? = null
    var shopItemClick : ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val cardLayout = when(viewType){
            DISABLE_ITEM -> R.layout.item_card_disable
            ENABLE_ITEM -> R.layout.item_card
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(cardLayout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.productName.text = item.name
        holder.productCount.text = item.count.toString()
        holder.view.setOnLongClickListener {
            shopItemLongClick?.invoke(item)
            true
        }
        holder.view.setOnClickListener {
            shopItemClick?.invoke(item)
            true
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enable){
            ENABLE_ITEM
        } else{
            DISABLE_ITEM
        }
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val productName: TextView = view.findViewById<TextView>(R.id.productName)
        val productCount: TextView = view.findViewById<TextView>(R.id.productCount)
    }

    companion object{
        private const val DISABLE_ITEM = 100
        private const val ENABLE_ITEM = 101
    }
}