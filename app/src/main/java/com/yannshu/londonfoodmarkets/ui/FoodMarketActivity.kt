package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import org.parceler.Parcels
import javax.inject.Inject

class FoodMarketActivity : BaseActivity(), FoodMarketActivityContract.View {

    @Inject
    internal lateinit var presenter: FoodMarketActivityPresenter

    private lateinit var market: FoodMarket

    companion object {
        private val KEY_MARKET = "market"

        fun getStartingIntent(context: Context, market: FoodMarket): Intent {
            val intent = Intent(context, FoodMarketActivity::class.java)
            intent.putExtra(KEY_MARKET, Parcels.wrap(market))
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        presenter.displayMarket()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubComponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(FoodMarketActivity::class.java) as FoodMarketActivityComponent.Builder)
                .activityModule(FoodMarketActivityComponent.FoodMarketActivityModule(this, market))
                .build()
                .injectMembers(this)
    }

    override fun retrieveIntentBundle(extras: Bundle?) {
        super.retrieveIntentBundle(extras)
        market = Parcels.unwrap(extras?.getParcelable(KEY_MARKET))
    }
}