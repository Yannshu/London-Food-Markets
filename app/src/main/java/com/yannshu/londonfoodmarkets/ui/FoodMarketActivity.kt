package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.config.GlideApp
import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_food_market.*
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
        setContentView(R.layout.activity_food_market)
        initActionBar()
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

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = market.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayPhoto(url: String) {
        GlideApp.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder_food_market)
                .into(photoImageView)
    }

    override fun displayAddress(name: String) {
        addressTextView.text = name
    }

    override fun getFormattedAddress(street: String, city: String, postcode: String): String {
        return getString(R.string.address_format, street, city, postcode)
    }

    override fun getUnknownAddress(): String {
        return getString(R.string.address_unknown)
    }

    override fun displayOpeningHours(openingHours: String) {
        openingHoursTextView.text = openingHours
    }

    override fun getFormattedOpeningHours(openingHour: String, closingHour: String): String {
        return getString(R.string.opening_hours_format, openingHour, closingHour)
    }

    override fun getClosed(): String {
        return getString(R.string.opening_hours_closed)
    }
}