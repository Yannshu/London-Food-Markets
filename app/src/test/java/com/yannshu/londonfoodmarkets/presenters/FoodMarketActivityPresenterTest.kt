package com.yannshu.londonfoodmarkets.presenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.Address
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.data.model.OpeningTime
import com.yannshu.londonfoodmarkets.utils.TimeConstants
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FoodMarketActivityPresenterTest : BasePresenterTest<FoodMarketActivityContract.View, FoodMarketActivityPresenter>() {

    companion object {
        private const val DESCRIPTION = "Biggest food market in London"
        private const val UNKNOWN_DESCRIPTION = "Yet another farmers market in London"
        private const val PHOTO_URL = "https://en.wikipedia.org/wiki/Borough_Market#/media/File:London_2018_March_IMG_0663.jpg"
        private const val STREET = "8 Southwark St"
        private const val CITY = "London"
        private const val POSTCODE = "SE1 1TL"
        private const val FORMATTED_ADDRESS = "$STREET, $CITY $POSTCODE"
        private const val UNKNOWN_ADDRESS = "Unknown address"
        private const val SATURDAY_OPENING_HOUR = "08:00"
        private const val SATURDAY_CLOSING_HOUR = "17:00"
        private const val SUNDAY_OPENING_HOUR = "10:00"
        private const val SUNDAY_CLOSING_HOUR = "17:00"
        private const val FORMATTED_SATURDAY_OPENING_TIMES = "$SATURDAY_OPENING_HOUR - $SATURDAY_CLOSING_HOUR"
        private const val FORMATTED_SUNDAY_OPENING_TIMES = "$SUNDAY_OPENING_HOUR - $SUNDAY_CLOSING_HOUR"
        private const val CLOSED = "Closed"
        private const val UNKNOWN_OPENING_HOURS = "Unknown opening hours"
        private const val WEBSITE = "http://boroughmarket.org.uk/"

        private const val SIZE_ZERO = 0
        private const val SIZE_SMALL = 5
        private const val SIZE_MEDIUM = 25
        private const val SIZE_BIG = 75
        private const val SIZE_VERY_BIG = 150
    }

    private val foodMarket = mock<FoodMarket>()

    @Before
    fun setUp() {
        view = mock()
        presenter = FoodMarketActivityPresenter(foodMarket, TimeConstants.SATURDAY)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun displayDescription() {
        whenever(foodMarket.description).thenReturn(DESCRIPTION)
        presenter.displayDescription()
        verify(view).displayDescription(DESCRIPTION)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayDescriptionNull() {
        whenever(foodMarket.description).thenReturn(null)
        whenever(view.getUnknownDescription()).thenReturn(UNKNOWN_DESCRIPTION)
        presenter.displayDescription()
        verify(view).getUnknownDescription()
        verify(view).displayDescription(UNKNOWN_DESCRIPTION)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayPhoto() {
        whenever(foodMarket.photos).thenReturn(listOf(PHOTO_URL))
        presenter.displayPhoto()
        verify(view).displayPhoto(PHOTO_URL)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayPhotoNull() {
        whenever(foodMarket.photos).thenReturn(null)
        presenter.displayPhoto()
        verify(view).displayPlaceholder()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayPhotoEmpty() {
        whenever(foodMarket.photos).thenReturn(listOf())
        presenter.displayPhoto()
        verify(view).displayPlaceholder()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayAddress() {
        whenever(foodMarket.address).thenReturn(Address(STREET, CITY, POSTCODE))
        whenever(view.getFormattedAddress(STREET, CITY, POSTCODE)).thenReturn(FORMATTED_ADDRESS)
        presenter.displayAddress()
        verify(view).getFormattedAddress(STREET, CITY, POSTCODE)
        verify(view).displayAddress(FORMATTED_ADDRESS)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayAddressIncompleteStreetNull() {
        whenever(foodMarket.address).thenReturn(Address(null, CITY, POSTCODE))
        whenever(view.getUnknownAddress()).thenReturn(UNKNOWN_ADDRESS)
        presenter.displayAddress()
        verify(view).getUnknownAddress()
        verify(view).displayAddress(UNKNOWN_ADDRESS)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayAddressIncompleteCityNull() {
        whenever(foodMarket.address).thenReturn(Address(STREET, null, POSTCODE))
        whenever(view.getUnknownAddress()).thenReturn(UNKNOWN_ADDRESS)
        presenter.displayAddress()
        verify(view).getUnknownAddress()
        verify(view).displayAddress(UNKNOWN_ADDRESS)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayAddressIncompletePostcodeNull() {
        whenever(foodMarket.address).thenReturn(Address(STREET, CITY, null))
        whenever(view.getUnknownAddress()).thenReturn(UNKNOWN_ADDRESS)
        presenter.displayAddress()
        verify(view).getUnknownAddress()
        verify(view).displayAddress(UNKNOWN_ADDRESS)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayAddressNull() {
        whenever(foodMarket.address).thenReturn(null)
        whenever(view.getUnknownAddress()).thenReturn(UNKNOWN_ADDRESS)
        presenter.displayAddress()
        verify(view).getUnknownAddress()
        verify(view).displayAddress(UNKNOWN_ADDRESS)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayOpeningHours() {
        whenever(foodMarket.openingTimes).thenReturn(createFakeOpeningTimes())
        whenever(view.getFormattedOpeningHours(SATURDAY_OPENING_HOUR, SATURDAY_CLOSING_HOUR)).thenReturn(FORMATTED_SATURDAY_OPENING_TIMES)
        whenever(view.getFormattedOpeningHours(SUNDAY_OPENING_HOUR, SUNDAY_CLOSING_HOUR)).thenReturn(FORMATTED_SUNDAY_OPENING_TIMES)
        whenever(view.getClosed()).thenReturn(CLOSED)
        presenter.displayOpeningHours()
        verify(view, times(2)).getFormattedOpeningHours(SATURDAY_OPENING_HOUR, SATURDAY_CLOSING_HOUR)
        verify(view).displayOpeningHoursForToday(FORMATTED_SATURDAY_OPENING_TIMES)
        verify(view).highlightOpeningHoursDay(TimeConstants.SATURDAY)
        verify(view).getFormattedOpeningHours(SUNDAY_OPENING_HOUR, SUNDAY_CLOSING_HOUR)
        verify(view, times(5)).getClosed()
        verify(view).displayOpeningHoursForDay(TimeConstants.MONDAY, CLOSED)
        verify(view).displayOpeningHoursForDay(TimeConstants.TUESDAY, CLOSED)
        verify(view).displayOpeningHoursForDay(TimeConstants.WEDNESDAY, CLOSED)
        verify(view).displayOpeningHoursForDay(TimeConstants.THURSDAY, CLOSED)
        verify(view).displayOpeningHoursForDay(TimeConstants.FRIDAY, CLOSED)
        verify(view).displayOpeningHoursForDay(TimeConstants.SATURDAY, FORMATTED_SATURDAY_OPENING_TIMES)
        verify(view).displayOpeningHoursForDay(TimeConstants.SUNDAY, FORMATTED_SUNDAY_OPENING_TIMES)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun getOpeningTimesForDay() {
        whenever(view.getFormattedOpeningHours(SATURDAY_OPENING_HOUR, SATURDAY_CLOSING_HOUR)).thenReturn(FORMATTED_SATURDAY_OPENING_TIMES)
        val openingTimesForToday = presenter.getOpeningTimesForDay(createFakeOpeningTimes(), TimeConstants.SATURDAY)
        Assert.assertEquals(FORMATTED_SATURDAY_OPENING_TIMES, openingTimesForToday)
        verify(view).getFormattedOpeningHours(SATURDAY_OPENING_HOUR, SATURDAY_CLOSING_HOUR)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun getOpeningTimesForDayOpeningTimesNull() {
        whenever(view.getUnknownOpeningHours()).thenReturn(UNKNOWN_OPENING_HOURS)
        val openingTimesForToday = presenter.getOpeningTimesForDay(null, TimeConstants.SATURDAY)
        Assert.assertEquals(UNKNOWN_OPENING_HOURS, openingTimesForToday)
        verify(view).getUnknownOpeningHours()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun getOpeningTimesForDayClosed() {
        whenever(view.getClosed()).thenReturn(CLOSED)
        val openingTimesForToday = presenter.getOpeningTimesForDay(createFakeOpeningTimes(), TimeConstants.MONDAY)
        Assert.assertEquals(CLOSED, openingTimesForToday)
        verify(view).getClosed()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayWebsite() {
        whenever(foodMarket.website).thenReturn(WEBSITE)
        presenter.displayWebsite()
        verify(view).displayWebsite(WEBSITE)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayWebsiteNull() {
        whenever(foodMarket.website).thenReturn(null)
        presenter.displayWebsite()
        verify(view).hideWebsite()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayWebsiteEmpty() {
        whenever(foodMarket.website).thenReturn("")
        presenter.displayWebsite()
        verify(view).hideWebsite()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayDetailsLayoutSizeValidCategoriesInvalid() {
        whenever(foodMarket.size).thenReturn(SIZE_SMALL)
        whenever(foodMarket.categories).thenReturn(null)
        presenter.displayDetailsLayout()
        verify(view).showDetails()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayDetailsLayoutSizeInvalidCategoriesValid() {
        whenever(foodMarket.size).thenReturn(SIZE_ZERO)
        whenever(foodMarket.categories).thenReturn(listOf(FoodMarket.CATEGORY_FARMERS, FoodMarket.CATEGORY_STREET_FOOD))
        presenter.displayDetailsLayout()
        verify(view).showDetails()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayDetailsLayoutSizeAndCategoriesValid() {
        whenever(foodMarket.size).thenReturn(SIZE_SMALL)
        whenever(foodMarket.categories).thenReturn(listOf(FoodMarket.CATEGORY_FARMERS, FoodMarket.CATEGORY_STREET_FOOD))
        presenter.displayDetailsLayout()
        verify(view).showDetails()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayDetailsLayoutSizeAndCategoriesInvalid() {
        whenever(foodMarket.size).thenReturn(SIZE_ZERO)
        whenever(foodMarket.categories).thenReturn(null)
        presenter.displayDetailsLayout()
        verify(view).hideDetails()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displaySizeZero() {
        whenever(foodMarket.size).thenReturn(SIZE_ZERO)
        presenter.displaySize()
        verify(view).hideSize()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displaySizeSmall() {
        whenever(foodMarket.size).thenReturn(SIZE_SMALL)
        presenter.displaySize()
        verify(view).showDetails()
        verify(view).showSize(R.string.size_small)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displaySizeMedium() {
        whenever(foodMarket.size).thenReturn(SIZE_MEDIUM)
        presenter.displaySize()
        verify(view).showDetails()
        verify(view).showSize(R.string.size_medium)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displaySizeBig() {
        whenever(foodMarket.size).thenReturn(SIZE_BIG)
        presenter.displaySize()
        verify(view).showDetails()
        verify(view).showSize(R.string.size_big)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displaySizeVeryBig() {
        whenever(foodMarket.size).thenReturn(SIZE_VERY_BIG)
        presenter.displaySize()
        verify(view).showDetails()
        verify(view).showSize(R.string.size_very_big)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayCategoriesNull() {
        whenever(foodMarket.categories).thenReturn(null)
        presenter.displayCategories()
        verify(view).hideFarmersStalls()
        verify(view).hideStreetFoodStands()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayCategoriesEmpty() {
        whenever(foodMarket.categories).thenReturn(listOf())
        presenter.displayCategories()
        verify(view).hideFarmersStalls()
        verify(view).hideStreetFoodStands()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayCategoriesFarmers() {
        whenever(foodMarket.categories).thenReturn(listOf(FoodMarket.CATEGORY_FARMERS))
        presenter.displayCategories()
        verify(view).hideFarmersStalls()
        verify(view).hideStreetFoodStands()
        verify(view).showDetails()
        verify(view).showFarmersStalls()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayCategoriesStreetFood() {
        whenever(foodMarket.categories).thenReturn(listOf(FoodMarket.CATEGORY_STREET_FOOD))
        presenter.displayCategories()
        verify(view).hideFarmersStalls()
        verify(view).hideStreetFoodStands()
        verify(view).showDetails()
        verify(view).showStreetFoodStands()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun displayCategoriesFarmersAndStreetFood() {
        whenever(foodMarket.categories).thenReturn(listOf(FoodMarket.CATEGORY_FARMERS, FoodMarket.CATEGORY_STREET_FOOD))
        presenter.displayCategories()
        verify(view).hideFarmersStalls()
        verify(view).hideStreetFoodStands()
        verify(view).showDetails()
        verify(view).showFarmersStalls()
        verify(view).showStreetFoodStands()
        verifyNoMoreInteractions(view)
    }

    private fun createFakeOpeningTimes() =
        listOf(
            createFakeOpeningTime(TimeConstants.SATURDAY, SATURDAY_OPENING_HOUR, SATURDAY_CLOSING_HOUR),
            createFakeOpeningTime(TimeConstants.SUNDAY, SUNDAY_OPENING_HOUR, SUNDAY_CLOSING_HOUR)
        )

    private fun createFakeOpeningTime(dayOfWeek: String, openingHour: String, closingHour: String) =
        OpeningTime(dayOfWeek, openingHour, closingHour)
}