package com.yannshu.londonfoodmarkets.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.data.model.FoodMarketClusterItem
import com.yannshu.londonfoodmarkets.extensions.safeStartActivityWithViewActionAndErrorDisplay
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.adapters.FoodMarketsAdapter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import com.yannshu.londonfoodmarkets.utils.AdsWrapper
import com.yannshu.londonfoodmarkets.utils.analytics.AnalyticsEvent.ABOUT_VIEWED
import com.yannshu.londonfoodmarkets.utils.analytics.AnalyticsEvent.ARG_NAME
import com.yannshu.londonfoodmarkets.utils.analytics.AnalyticsEvent.MARKET_VIEWED
import com.yannshu.londonfoodmarkets.utils.analytics.AnalyticsEvent.SUGGEST_MISSING_MARKET_VIEWED
import com.yannshu.londonfoodmarkets.utils.analytics.AnalyticsWrapper
import kotlinx.android.synthetic.main.activity_main.adView
import kotlinx.android.synthetic.main.activity_main.foodMarketsRecyclerView
import kotlinx.android.synthetic.main.activity_main.mapView
import kotlinx.android.synthetic.main.activity_main.rootLayout
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    companion object {
        fun getStartingIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    internal lateinit var presenter: MainActivityPresenter

    @Inject
    internal lateinit var foodMarketAdapter: FoodMarketsAdapter

    @Inject
    internal lateinit var adsWrapper: AdsWrapper

    private var map: GoogleMap? = null

    private var locationClient: FusedLocationProviderClient? = null

    private var locationPermissionGranted = false

    private var openTodayCheckBox: CheckBox? = null

    private var clusterManager: ClusterManager<FoodMarketClusterItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView.onCreate(savedInstanceState)
        initToolbar()
        initFoodMarketsRecyclerView()
        presenter.attachView(this)
        requestLocationPermission()
        initMap()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        map?.let {
            val cameraPosition = it.cameraPosition
            presenter.saveMapCameraPosition(cameraPosition.target.latitude.toFloat(), cameraPosition.target.longitude.toFloat(),
                cameraPosition.zoom, cameraPosition.bearing, cameraPosition.tilt)
        }
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        presenter.destroyData()
        presenter.detachView()
        destroyMap()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        openTodayCheckBox = menu?.findItem(R.id.open_today)?.actionView as CheckBox
        openTodayCheckBox?.setText(R.string.open_today)
        openTodayCheckBox?.setOnCheckedChangeListener { _, isChecked ->
            presenter.filterMarkets(isChecked)
        }
        openTodayCheckBox?.isEnabled = presenter.foodMarketsLoaded()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.suggest_market -> {
                openSuggestMissingMarket()
                true
            }
            R.id.about -> {
                openAboutActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initMap() {
        mapView.getMapAsync { map: GoogleMap ->
            onMapLoaded(map)
        }
    }

    private fun onMapLoaded(map: GoogleMap) {
        this.map = map
        val localClusterManager = ClusterManager<FoodMarketClusterItem>(this, map)
        localClusterManager.renderer = MarketClusterRenderer(this, map, localClusterManager)
        localClusterManager.setOnClusterClickListener {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(map.cameraPosition.target, map.cameraPosition.zoom + 1.0f))
            it.size > 0
        }
        localClusterManager.setOnClusterItemClickListener {
            onFoodMarketClick(it.market)
            true
        }
        map.setOnMarkerClickListener(localClusterManager)
        map.setOnCameraIdleListener(localClusterManager)

        clusterManager = localClusterManager

        presenter.positionMapCenter()
        presenter.loadData()
        if (locationPermissionGranted) {
            showUserLocationOnMap()
        }
        initAds()
    }

    private fun destroyMap() {
        map = null
    }

    private fun requestLocationPermission() {
        val permissionListener = object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                locationPermissionGranted = true
                initLocation()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                showLocationPermissionRationale(token)
            }
        }

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(permissionListener)
            .check()
    }

    private fun showLocationPermissionRationale(token: PermissionToken?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_location_rationale_title)
            .setMessage(R.string.permission_location_rationale_message)
            .setOnDismissListener { _ -> token?.cancelPermissionRequest() }
            .setNegativeButton(R.string.cancel) { _, _ -> token?.cancelPermissionRequest() }
            .setPositiveButton(R.string.ok) { _, _ -> token?.continuePermissionRequest() }
            .show()
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        showUserLocationOnMap()
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationClient?.lastLocation?.addOnSuccessListener { location ->
            location?.let {
                presenter.onLocationLoaded(it.latitude, it.longitude)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showUserLocationOnMap() {
        map?.let {
            it.isMyLocationEnabled = true
        }
    }

    override fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float, bearing: Float, tilt: Float) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(lat, lng))
            .zoom(zoom)
            .bearing(bearing)
            .tilt(tilt)
            .build()
        map?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun clearMarkets() {
        clusterManager?.clearItems()
    }

    override fun addMarket(market: FoodMarket) {
        clusterManager?.addItem(FoodMarketClusterItem(market))
    }

    override fun renderMarkets() {
        clusterManager?.cluster()
    }

    private fun initFoodMarketsRecyclerView() {
        foodMarketsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        foodMarketsRecyclerView.setHasFixedSize(true)
        foodMarketsRecyclerView.adapter = foodMarketAdapter

        foodMarketAdapter.listener = object : FoodMarketsAdapter.Listener {
            override fun onClick(foodMarket: FoodMarket) {
                onFoodMarketClick(foodMarket)
            }
        }
    }

    override fun displayFoodMarketsRecyclerView(foodMarkets: List<FoodMarket>) {
        if (foodMarketsRecyclerView.visibility != View.VISIBLE) {
            map?.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.food_market_recycler_view_height))
            foodMarketsRecyclerView.visibility = View.VISIBLE
        }

        foodMarketAdapter.foodMarkets = foodMarkets
        foodMarketAdapter.notifyDataSetChanged()
    }

    override fun setOpenTodayEnabled(enabled: Boolean) {
        openTodayCheckBox?.isEnabled = enabled
    }

    private fun onFoodMarketClick(foodMarket: FoodMarket) {
        val intent = FoodMarketActivity.getStartingIntent(this, foodMarket)
        startActivity(intent)

        AnalyticsWrapper.logEvent(this, MARKET_VIEWED, Bundle().apply { putString(ARG_NAME, foodMarket.name) })
    }

    private fun openSuggestMissingMarket() {
        safeStartActivityWithViewActionAndErrorDisplay(getString(R.string.suggest_market_google_form), rootLayout)

        AnalyticsWrapper.logEvent(this, SUGGEST_MISSING_MARKET_VIEWED)
    }

    private fun openAboutActivity() {
        val intent = AboutActivity.getStartingIntent(this)
        startActivity(intent)

        AnalyticsWrapper.logEvent(this, ABOUT_VIEWED)
    }

    private fun initAds() {
        adsWrapper.init(this)
        adsWrapper.loadAd(adView)
    }
}
