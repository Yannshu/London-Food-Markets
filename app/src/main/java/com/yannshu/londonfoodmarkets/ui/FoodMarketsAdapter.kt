package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.config.GlideApp
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import kotlinx.android.synthetic.main.item_food_market.view.foodMarketNameTextView
import kotlinx.android.synthetic.main.item_food_market.view.foodMarketPhotoImageView

class FoodMarketsAdapter(private val context: Context) : RecyclerView.Adapter<FoodMarketsAdapter.FoodMarketViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    var foodMarkets: List<FoodMarket>? = null

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodMarketViewHolder {
        val view = layoutInflater.inflate(R.layout.item_food_market, parent, false)
        return FoodMarketViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: FoodMarketViewHolder?, position: Int) {
        holder?.bind(foodMarkets!![position], listener)
    }

    override fun getItemCount(): Int {
        return foodMarkets?.size ?: 0
    }

    class FoodMarketViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

        fun bind(foodMarket: FoodMarket, listener: Listener?) {
            itemView.foodMarketNameTextView.text = foodMarket.name
            val photos = foodMarket.photos
            if (photos != null && !photos.isEmpty()) {
                GlideApp.with(context)
                        .load(photos[FoodMarket.FIRST_PHOTO_INDEX])
                        .placeholder(R.drawable.placeholder_food_market)
                        .into(itemView.foodMarketPhotoImageView)
            } else {
                itemView.foodMarketPhotoImageView.setImageResource(R.drawable.placeholder_food_market)
            }

            if (listener != null) {
                itemView.setOnClickListener { listener.onClick(foodMarket) }
            }
        }
    }

    interface Listener {
        fun onClick(foodMarket: FoodMarket)
    }
}