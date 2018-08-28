package com.yannshu.londonfoodmarkets.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class FoodMarketClusterItem(val market: FoodMarket) : ClusterItem {
    override fun getSnippet() = market.name

    override fun getTitle() = market.name

    override fun getPosition() = LatLng(market.coordinates!!.latitude, market.coordinates!!.longitude)
}