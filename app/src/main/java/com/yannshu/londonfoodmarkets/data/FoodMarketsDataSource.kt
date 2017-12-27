package com.yannshu.londonfoodmarkets.data

import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class FoodMarketsDataSource(private val databases: FirebaseFirestore) {

    companion object {
        val FOOD_MARKETS = "food-markets"
    }

    fun getFoodMarkets() {
        databases.collection(FOOD_MARKETS)
                .get()
                .addOnCompleteListener({ task -> Timber.d(task.result.documents[0].data.toString()) })
    }
}