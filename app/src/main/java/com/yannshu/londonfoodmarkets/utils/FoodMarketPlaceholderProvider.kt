package com.yannshu.londonfoodmarkets.utils

import com.yannshu.londonfoodmarkets.R
import java.util.Random

class FoodMarketPlaceholderProvider {

    private val resourceArray = intArrayOf(R.drawable.ic_fishes, R.drawable.ic_sausages, R.drawable.ic_stall, R.drawable.ic_vegetables)

    private val random = Random()

    fun getRandomPlaceHolder(): Int = resourceArray[random.nextInt(resourceArray.size)]
}