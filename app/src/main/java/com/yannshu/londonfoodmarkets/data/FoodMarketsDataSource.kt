package com.yannshu.londonfoodmarkets.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.yannshu.londonfoodmarkets.data.model.FoodMarket

class FoodMarketsDataSource(private val databases: FirebaseFirestore, private val objectMapper: ObjectMapper) {

    companion object {
        val FOOD_MARKETS = "food-markets"
    }

    var listener: Listener? = null

    fun loadFoodMarkets() {
        databases.collection(FOOD_MARKETS)
                .get()
                .addOnFailureListener { listener?.onFailure() }
                .addOnCompleteListener { task ->
                    run {
                        val documents = task.result.documents
                        val foodMarkets = ArrayList<FoodMarket>(documents.size)
                        documents.forEach { document -> foodMarkets.add(objectMapper.convertValue(document.data, FoodMarket::class.java)) }

                        listener?.onComplete(foodMarkets)
                    }
                }
    }

    interface Listener {
        fun onFailure()
        fun onComplete(foodMarkets: List<FoodMarket>)
    }
}