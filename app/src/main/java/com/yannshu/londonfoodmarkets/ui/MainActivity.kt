package com.yannshu.londonfoodmarkets.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    internal lateinit var presenter: MainActivityPresenter

    private var mapFragment: SupportMapFragment? = null

    private var map: GoogleMap? = null

    private var locationClient: FusedLocationProviderClient? = null

    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        presenter.loadData()
        requestLocationPermission()
        initMap()
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

    private fun initMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment?.getMapAsync { map: GoogleMap ->
            this.map = map
            map.setOnMarkerClickListener { marker: Marker ->
                onMarkerClick(marker)
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

    private fun initLocation() {
        showUserLocationOnMap()
        locationClient = LocationServices.getFusedLocationProviderClient(this)
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

    private fun onMarkerClick(marker: Marker) {
        val intent = FoodMarketActivity.getStartingIntent(this, marker.tag as FoodMarket)
        startActivity(intent)
    }
}
