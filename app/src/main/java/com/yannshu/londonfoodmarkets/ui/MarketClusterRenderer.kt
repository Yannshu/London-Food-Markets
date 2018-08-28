package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.FoodMarketClusterItem
import com.yannshu.londonfoodmarkets.utils.VectorDescriptorFactory

private const val RENDER_AS_CLUSTER_THRESHOLD = 3

class MarketClusterRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<FoodMarketClusterItem>
) : DefaultClusterRenderer<FoodMarketClusterItem>(context, map, clusterManager) {

    private val bitmapDescriptors: SparseArray<BitmapDescriptor> = SparseArray()

    private val iconGenerator = IconGenerator(context).apply {
        setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        setTextAppearance(R.style.Cluster_TextAppearance)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<FoodMarketClusterItem>, markerOptions: MarkerOptions) {
        val icon = iconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun onBeforeClusterItemRendered(item: FoodMarketClusterItem, markerOptions: MarkerOptions) {
        val market = item.market
        var imageDescriptor = bitmapDescriptors.get(market.drawableRes)
        if (imageDescriptor == null) {
            imageDescriptor = VectorDescriptorFactory.fromVector(context, market.drawableRes)
            bitmapDescriptors.put(market.drawableRes, imageDescriptor)
        }
        markerOptions.icon(imageDescriptor)
        markerOptions.title(market.name)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<FoodMarketClusterItem>) =
        cluster.size > RENDER_AS_CLUSTER_THRESHOLD
}