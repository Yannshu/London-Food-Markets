package com.yannshu.londonfoodmarkets.utils

import com.yannshu.londonfoodmarkets.R

class FoodMarketPlaceholderProvider {

    private val resourceArray = intArrayOf(R.drawable.ic_fishes, R.drawable.ic_sausages, R.drawable.ic_stall, R.drawable.ic_vegetables)

    private var nextIconIndex = 0

    fun getRandomPlaceHolder(): Int {
        val iconRes = resourceArray[nextIconIndex]
        nextIconIndex = (nextIconIndex + 1) % resourceArray.size
        return iconRes
    }
}