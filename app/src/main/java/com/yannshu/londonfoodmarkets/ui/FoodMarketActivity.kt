package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.config.GlideApp
import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.extensions.safeStartActivityWithViewAction
import com.yannshu.londonfoodmarkets.presenters.FoodMarketActivityPresenter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import com.yannshu.londonfoodmarkets.utils.FoodMarketPlaceholderProvider
import com.yannshu.londonfoodmarkets.utils.TimeConstants
import kotlinx.android.synthetic.main.activity_food_market.addressTextView
import kotlinx.android.synthetic.main.activity_food_market.descriptionTextView
import kotlinx.android.synthetic.main.activity_food_market.detailsLayout
import kotlinx.android.synthetic.main.activity_food_market.expandHoursImageView
import kotlinx.android.synthetic.main.activity_food_market.farmersStallsTextView
import kotlinx.android.synthetic.main.activity_food_market.fridayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.fridayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.mondayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.mondayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.openingHoursExpandableLayout
import kotlinx.android.synthetic.main.activity_food_market.photoImageView
import kotlinx.android.synthetic.main.activity_food_market.rootLayout
import kotlinx.android.synthetic.main.activity_food_market.saturdayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.saturdayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.sizeTextView
import kotlinx.android.synthetic.main.activity_food_market.streetFoodTextView
import kotlinx.android.synthetic.main.activity_food_market.sundayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.sundayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.thursdayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.thursdayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.todayHoursLayout
import kotlinx.android.synthetic.main.activity_food_market.todayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.toolbar
import kotlinx.android.synthetic.main.activity_food_market.tuesdayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.tuesdayHoursTextView
import kotlinx.android.synthetic.main.activity_food_market.websiteLayout
import kotlinx.android.synthetic.main.activity_food_market.websiteTextView
import kotlinx.android.synthetic.main.activity_food_market.wednesdayDayTextView
import kotlinx.android.synthetic.main.activity_food_market.wednesdayHoursTextView
import org.parceler.Parcels
import javax.inject.Inject

class FoodMarketActivity : BaseActivity(), FoodMarketActivityContract.View {

    @Inject
    internal lateinit var presenter: FoodMarketActivityPresenter

    @Inject
    internal lateinit var foodMarketPlaceholderProvider: FoodMarketPlaceholderProvider

    private lateinit var market: FoodMarket

    private val dayOfWeekViews = HashMap<String, Pair<TextView, TextView>>()

    companion object {
        private const val VIEW_ROTATION_180_DEGREE = 180
        private const val KEY_MARKET = "market"

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
        initDayOfWeekViews()
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

    private fun initDayOfWeekViews() {
        dayOfWeekViews[TimeConstants.MONDAY] = Pair(mondayDayTextView, mondayHoursTextView)
        dayOfWeekViews[TimeConstants.TUESDAY] = Pair(tuesdayDayTextView, tuesdayHoursTextView)
        dayOfWeekViews[TimeConstants.WEDNESDAY] = Pair(wednesdayDayTextView, wednesdayHoursTextView)
        dayOfWeekViews[TimeConstants.THURSDAY] = Pair(thursdayDayTextView, thursdayHoursTextView)
        dayOfWeekViews[TimeConstants.FRIDAY] = Pair(fridayDayTextView, fridayHoursTextView)
        dayOfWeekViews[TimeConstants.SATURDAY] = Pair(saturdayDayTextView, saturdayHoursTextView)
        dayOfWeekViews[TimeConstants.SUNDAY] = Pair(sundayDayTextView, sundayHoursTextView)

        todayHoursLayout.setOnClickListener {
            expandOrCollapseOpeningHoursExpandableLayout()
        }

        openingHoursExpandableLayout.setOnClickListener {
            expandOrCollapseOpeningHoursExpandableLayout()
        }

        openingHoursExpandableLayout.setOnExpansionUpdateListener { expansionFraction, _ -> expandHoursImageView.rotation = expansionFraction * VIEW_ROTATION_180_DEGREE }
    }

    private fun expandOrCollapseOpeningHoursExpandableLayout() {
        if (openingHoursExpandableLayout.isExpanded) {
            openingHoursExpandableLayout.collapse()
        } else {
            openingHoursExpandableLayout.expand()
        }
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

    override fun displayDescription(description: String) {
        descriptionTextView.text = description
    }

    override fun getUnknownDescription(): String = getString(R.string.description_unknown)

    override fun displayPhoto(url: String) {
        val placeholder = foodMarketPlaceholderProvider.getRandomPlaceHolder()
        GlideApp.with(this)
                .load(url)
                .placeholder(placeholder)
                .error(placeholder)
                .into(photoImageView)
    }

    override fun displayPlaceholder() {
        photoImageView.setImageResource(foodMarketPlaceholderProvider.getRandomPlaceHolder())
    }

    override fun displayAddress(address: String) {
        addressTextView.text = address
    }

    override fun getFormattedAddress(street: String, city: String, postcode: String): String {
        return getString(R.string.address_format, street, city, postcode)
    }

    override fun getUnknownAddress(): String {
        return getString(R.string.address_unknown)
    }

    override fun displayOpeningHoursForToday(openingHours: String) {
        todayHoursTextView.text = getString(R.string.opening_hours_today, openingHours)
    }

    override fun highlightOpeningHoursDay(day: String) {
        dayOfWeekViews[day]?.let {
            it.first.setTypeface(it.first.typeface, Typeface.BOLD)
            it.second.setTypeface(it.second.typeface, Typeface.BOLD)
        }
    }

    override fun displayOpeningHoursForDay(day: String, openingHours: String) {
        dayOfWeekViews[day]?.let {
            it.first.text = day.capitalize()
            it.second.text = openingHours
        }
    }

    override fun getFormattedOpeningHours(openingHour: String, closingHour: String): String {
        return getString(R.string.opening_hours_format, openingHour, closingHour)
    }

    override fun getClosed(): String {
        return getString(R.string.opening_hours_closed)
    }

    override fun getUnknownOpeningHours(): String {
        return getString(R.string.opening_hours_unknown)
    }

    override fun displayWebsite(url: String) {
        websiteLayout.visibility = View.VISIBLE
        websiteLayout.setOnClickListener { openBrowser(url) }
        websiteTextView.text = url
    }

    override fun hideWebsite() {
        websiteLayout.visibility = View.GONE
    }

    private fun openBrowser(url: String) {
        if (!safeStartActivityWithViewAction(url)) {
            Snackbar.make(rootLayout, R.string.install_browser, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showDetails() {
        detailsLayout.visibility = View.VISIBLE
    }

    override fun hideDetails() {
        detailsLayout.visibility = View.GONE
    }

    override fun showFarmersStalls() {
        farmersStallsTextView.visibility = View.VISIBLE
    }

    override fun hideFarmersStalls() {
        farmersStallsTextView.visibility = View.GONE
    }

    override fun showStreetFoodStands() {
        streetFoodTextView.visibility = View.VISIBLE
    }

    override fun hideStreetFoodStands() {
        streetFoodTextView.visibility = View.GONE
    }

    override fun hideSize() {
        sizeTextView.visibility = View.GONE
    }

    override fun showSize(resId: Int) {
        sizeTextView.visibility = View.VISIBLE
        sizeTextView.setText(resId)
    }
}