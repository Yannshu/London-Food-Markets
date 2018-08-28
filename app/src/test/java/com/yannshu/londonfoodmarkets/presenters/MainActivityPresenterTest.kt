package com.yannshu.londonfoodmarkets.presenters

import com.google.firebase.firestore.GeoPoint
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.data.model.OpeningTime
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainActivityPresenterTest : BasePresenterTest<MainActivityContract.View, MainActivityPresenter>() {

    companion object {
        private const val TODAY = "2018-07-28"
        private const val YESTERDAY = "2018-07-27"
        private const val LATITUDE = 51.505460.toFloat()
        private const val LONGITUDE = (-0.091050).toFloat()
        private const val ZOOM = 12.toFloat()
        private const val BEARING = 0.toFloat()
        private const val TILT = 0.toFloat()
        private const val DEFAULT_FOOD_MARKET_LIST_SIZE = 2
        private const val FILTERED_FOOD_MARKET_LIST_SIZE = 1

        private const val FOOD_MARKET_0_NAME = "Borough Market"
        private const val FOOD_MARKET_0_LAT = LATITUDE.toDouble()
        private const val FOOD_MARKET_0_LNG = LONGITUDE.toDouble()
        private const val LOCATION_NEAR_FOOD_MARKET_0_LAT = 51.499733
        private const val LOCATION_NEAR_FOOD_MARKET_0_LNG = -0.076261

        private const val FOOD_MARKET_1_NAME = "Brockley Market"
        private const val FOOD_MARKET_1_LAT = 51.467922
        private const val FOOD_MARKET_1_LNG = -0.024654
        private const val LOCATION_NEAR_FOOD_MARKET_1_LAT = 51.474820
        private const val LOCATION_NEAR_FOOD_MARKET_1_LNG = -0.046795
    }

    private val foodMarketDataSource = mock<FoodMarketsDataSource>()

    private val mapsUtils = MapsUtils()

    private val mapCameraPositionSaver = mock<MapCameraPositionSaver> {
        on { getLatitude() } doReturn LATITUDE
        on { getLongitude() } doReturn LONGITUDE
        on { getZoom() } doReturn ZOOM
        on { getBearing() } doReturn BEARING
        on { getTilt() } doReturn TILT
    }

    @Before
    fun setUp() {
        view = mock()
        presenter = MainActivityPresenter(foodMarketDataSource, mapsUtils, mapCameraPositionSaver, TODAY)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun loadData() {
        presenter.loadData()
        verify(foodMarketDataSource).listener = any()
        verify(foodMarketDataSource).loadFoodMarkets()
        verifyNoMoreInteractions(foodMarketDataSource)
    }

    @Test
    fun saveMapCameraPosition() {
        presenter.saveMapCameraPosition(LATITUDE, LONGITUDE, ZOOM, BEARING, TILT)
        verify(mapCameraPositionSaver).saveMapCenterPosition(LATITUDE, LONGITUDE, ZOOM, BEARING, TILT)
        verifyNoMoreInteractions(mapCameraPositionSaver)
    }

    @Test
    fun positionMapCenterPositionSavedRecently() {
        whenever(mapCameraPositionSaver.hasCameraPositionBeenSavedRecently()).thenReturn(true)
        presenter.positionMapCenter()
        verify(mapCameraPositionSaver).hasCameraPositionBeenSavedRecently()
        verify(mapCameraPositionSaver).getLatitude()
        verify(mapCameraPositionSaver).getLongitude()
        verify(mapCameraPositionSaver).getZoom()
        verify(mapCameraPositionSaver).getBearing()
        verify(mapCameraPositionSaver).getTilt()
        verify(view).moveMapCenterTo(LATITUDE.toDouble(), LONGITUDE.toDouble(), ZOOM, BEARING, TILT)
        verifyNoMoreInteractions(mapCameraPositionSaver, view)
    }

    @Test
    fun positionMapCenterNoPositionSaved() {
        whenever(mapCameraPositionSaver.hasCameraPositionBeenSavedRecently()).thenReturn(false)
        presenter.positionMapCenter()
        verify(mapCameraPositionSaver).hasCameraPositionBeenSavedRecently()
        verify(view).moveMapCenterTo(MainActivityPresenter.LONDON_LAT, MainActivityPresenter.LONDON_LNG, MainActivityPresenter.DEFAULT_ZOOM)
        verifyNoMoreInteractions(mapCameraPositionSaver, view)
    }

    @Test
    fun displayFoodMarkets() {
        val foodMarkets = createFakeFoodMarketsWithData()
        presenter.displayFoodMarkets(foodMarkets)
        verify(view).clearMarkets()
        verify(view, times(DEFAULT_FOOD_MARKET_LIST_SIZE)).addMarket(any())
        verify(view).renderMarkets()
        verify(view).displayFoodMarketsRecyclerView(foodMarkets)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun onLocationLoaded() {
        presenter.foodMarkets = createFakeFoodMarketsWithData()
        presenter.onLocationLoaded(LOCATION_NEAR_FOOD_MARKET_0_LAT, LOCATION_NEAR_FOOD_MARKET_0_LNG)
        verify(view).displayFoodMarketsRecyclerView(any())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun sortFoodMarketByDistanceTo() {
        var sortedFoodMarket = presenter.sortFoodMarketByDistanceTo(createFakeFoodMarketsWithData(), LOCATION_NEAR_FOOD_MARKET_0_LAT, LOCATION_NEAR_FOOD_MARKET_0_LNG)
        Assert.assertEquals(FOOD_MARKET_0_NAME, sortedFoodMarket[0].name)
        Assert.assertEquals(FOOD_MARKET_1_NAME, sortedFoodMarket[1].name)

        sortedFoodMarket = presenter.sortFoodMarketByDistanceTo(createFakeFoodMarketsWithData(), LOCATION_NEAR_FOOD_MARKET_1_LAT, LOCATION_NEAR_FOOD_MARKET_1_LNG)
        Assert.assertEquals(FOOD_MARKET_1_NAME, sortedFoodMarket[0].name)
        Assert.assertEquals(FOOD_MARKET_0_NAME, sortedFoodMarket[1].name)
    }

    @Test
    fun filterMarketsOpenToday() {
        presenter.foodMarkets = createFakeFoodMarketsWithData()
        presenter.filterMarkets(true)
        verify(view).clearMarkets()
        argumentCaptor<FoodMarket> {
            verify(view).addMarket(capture())
            Assert.assertEquals(FILTERED_FOOD_MARKET_LIST_SIZE, allValues.size)
            Assert.assertEquals(FOOD_MARKET_0_NAME, firstValue.name)
        }
        verify(view).renderMarkets()
        verify(view).displayFoodMarketsRecyclerView(any())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun filterMarketsNotOpenToday() {
        presenter.foodMarkets = createFakeFoodMarketsWithData()
        presenter.filterMarkets(false)
        verify(view).clearMarkets()
        verify(view, times(DEFAULT_FOOD_MARKET_LIST_SIZE)).addMarket(any())
        verify(view).renderMarkets()
        verify(view).displayFoodMarketsRecyclerView(any())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun foodMarketsLoaded() {
        presenter.foodMarkets = createFakeFoodMarketsWithData()
        var loaded = presenter.foodMarketsLoaded()
        Assert.assertEquals(true, loaded)

        presenter.foodMarkets = null
        loaded = presenter.foodMarketsLoaded()
        Assert.assertEquals(false, loaded)
    }

    private fun createFakeFoodMarketsWithData() =
        mutableListOf(
            createFakeMarket(FOOD_MARKET_0_NAME, FOOD_MARKET_0_LAT, FOOD_MARKET_0_LNG, TODAY),
            createFakeMarket(FOOD_MARKET_1_NAME, FOOD_MARKET_1_LAT, FOOD_MARKET_1_LNG, YESTERDAY)
        )

    private fun createFakeMarket(name: String, lat: Double, lng: Double, openingDay: String): FoodMarket {
        val foodMarket = FoodMarket()
        foodMarket.name = name
        foodMarket.coordinates = GeoPoint(lat, lng)
        foodMarket.openingTimes = listOf(createFakeOpeningTime(openingDay))
        return foodMarket
    }

    private fun createFakeOpeningTime(day: String): OpeningTime {
        val openingTime = OpeningTime()
        openingTime.day = day
        return openingTime
    }
}