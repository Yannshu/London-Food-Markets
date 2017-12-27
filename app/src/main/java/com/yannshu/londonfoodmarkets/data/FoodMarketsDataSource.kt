package com.yannshu.londonfoodmarkets.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import timber.log.Timber

class FoodMarketsDataSource(private val databases: FirebaseFirestore, private val objectMapper: ObjectMapper) {

    companion object {
        val FOOD_MARKETS = "food-markets"
    }

    fun getFoodMarkets() {
        databases.collection(FOOD_MARKETS)
                .get()
                .addOnCompleteListener({ task ->
                    run {
                        val foodMarket = objectMapper.convertValue(task.result.documents[0].data, FoodMarket::class.java)

                        Timber.d("foodMarket: " + foodMarket.name)
                    }
                })
    }
}