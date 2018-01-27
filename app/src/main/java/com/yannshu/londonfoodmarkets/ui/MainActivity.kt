package com.yannshu.londonfoodmarkets.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.adapters.FoodMarketsAdapter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import com.yannshu.londonfoodmarkets.utils.AdsWrapper
import kotlinx.android.synthetic.main.activity_main.adView
import kotlinx.android.synthetic.main.activity_main.foodMarketsRecyclerView
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    internal lateinit var presenter: MainActivityPresenter

    @Inject
    internal lateinit var foodMarketAdapter: FoodMarketsAdapter

    @Inject
    internal lateinit var adsWrapper: AdsWrapper

    private var mapFragment: SupportMapFragment? = null

    private var map: GoogleMap? = null

    private var locationClient: FusedLocationProviderClient? = null

    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        presenter.attachView(this)
        presenter.loadData()
        requestLocationPermission()
        initFoodMarketRecyclerView()
        initMap()
        initAds()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubComponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(MainActivity::class.java) as MainActivityComponent.Builder)
                .activityModule(MainActivityComponent.MainActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroyData()
        presenter.detachView()
        destroyMap()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.about -> {
                openAboutActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initFoodMarketRecyclerView() {
        foodMarketAdapter.listener = object : FoodMarketsAdapter.Listener {
            override fun onClick(foodMarket: FoodMarket) {
                onFoodMarketClick(foodMarket)
            }
        }

        foodMarketsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        foodMarketsRecyclerView.setHasFixedSize(true)
        foodMarketsRecyclerView.adapter = foodMarketAdapter
    }

    private fun initMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment?.getMapAsync { map: GoogleMap ->
            this.map = map
            map.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.food_market_recycler_view_height))
            map.setOnMarkerClickListener { marker: Marker ->
                onFoodMarketClick(marker.tag as FoodMarket)
                true
            }
            presenter.onMapLoaded()

            if (locationPermissionGranted) {
                showUserLocationOnMap()
            }
        }
    }

    private fun destroyMap() {
        mapFragment = null
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
                .setNegativeButton(R.string.cancel, { _, _ -> token?.cancelPermissionRequest() })
                .setPositiveButton(R.string.ok, { _, _ -> token?.continuePermissionRequest() })
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

    override fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), zoom))
    }

    override fun addMarket(market: FoodMarket) {
        val markerOptions = MarkerOptions()
                .title(market.name)
                .position(LatLng(market.coordinates!!.latitude, market.coordinates!!.longitude))
        val marker = map?.addMarker(markerOptions)
        marker?.tag = market
    }

    override fun displayFoodMarketList(foodMarkets: List<FoodMarket>) {
        foodMarketAdapter.foodMarkets = foodMarkets
        foodMarketAdapter.notifyDataSetChanged()
        foodMarketsRecyclerView.visibility = View.VISIBLE
    }

    private fun onFoodMarketClick(foodMarket: FoodMarket) {
        val intent = FoodMarketActivity.getStartingIntent(this, foodMarket)
        startActivity(intent)
    }

    private fun openAboutActivity() {
        val intent = AboutActivity.getStartingIntent(this)
        startActivity(intent)
    }

    private fun initAds() {
        adsWrapper.init(this)
        adsWrapper.loadAd(adView)
    }
}
