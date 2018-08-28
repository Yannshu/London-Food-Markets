package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.Icon
import com.yannshu.londonfoodmarkets.ui.adapters.IconsAdapter
import dagger.Module
import dagger.Provides

@Module
class AboutActivityModule {

    @Provides
    fun provideIconsAdapter(activity: AboutActivity, icons: List<Icon>): IconsAdapter = IconsAdapter(activity, icons)

    @Provides
    fun provideIcons(): List<Icon> =
        ArrayList<Icon>().apply {
            add(Icon(R.string.credit_icon_big_ben_description, R.string.credit_icon_big_ben_url, R.drawable.ic_big_ben))
            add(Icon(R.string.credit_icon_store_location_description, R.string.credit_icon_store_location_url, R.drawable.ic_market_marker))
            add(Icon(R.string.credit_icon_farmer_description, R.string.credit_icon_farmer_url, R.drawable.ic_farmer_marker))
            add(Icon(R.string.credit_icon_burger_description, R.string.credit_icon_burger_url, R.drawable.ic_street_food_marker))
            add(Icon(R.string.credit_icon_cow_description, R.string.credit_icon_cow_url, R.drawable.ic_cow))
            add(Icon(R.string.credit_icon_hot_dog_description, R.string.credit_icon_hot_dog_url, R.drawable.ic_hot_dog))
            add(Icon(R.string.credit_icon_size_description, R.string.credit_icon_size_url, R.drawable.ic_size))
            add(Icon(R.string.credit_icon_fishes_description, R.string.credit_icon_fishes_url, R.drawable.ic_fishes))
            add(Icon(R.string.credit_icon_sausages_description, R.string.credit_icon_sausages_url, R.drawable.ic_sausages))
            add(Icon(R.string.credit_icon_stall_description, R.string.credit_icon_stall_url, R.drawable.ic_stall))
            add(Icon(R.string.credit_icon_vegetables_description, R.string.credit_icon_vegetables_url, R.drawable.ic_vegetables))
        }
}
