package com.yannshu.londonfoodmarkets.ui

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    internal lateinit var presenter: MainActivityPresenter

    private var mapFragment: SupportMapFragment? = null

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        presenter.loadData()
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
            presenter.onMapLoaded()
        }
    }

    private fun destroyMap() {
        mapFragment = null
        map = null
    }

    override fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), zoom))
    }
}
