package com.yannshu.londonfoodmarkets.ui

import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.Icon
import com.yannshu.londonfoodmarkets.ui.adapters.IconsAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [AboutActivityComponent.AboutActivityModule::class])
interface AboutActivityComponent : AndroidInjector<AboutActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AboutActivity>()

    @Module
    class AboutActivityModule {

        @Provides
        fun provideIconsAdapter(activity: AboutActivity, icons: List<Icon>): IconsAdapter = IconsAdapter(activity, icons)

        @Provides
        fun provideIcons(): List<Icon> {
            val icons = ArrayList<Icon>()
            icons.add(Icon(R.string.credit_icon_big_ben_description, R.string.credit_icon_big_ben_url, R.drawable.ic_big_ben))
            icons.add(Icon(R.string.credit_icon_store_location_description, R.string.credit_icon_store_location_url, R.drawable.ic_market_marker))
            icons.add(Icon(R.string.credit_icon_cow_description, R.string.credit_icon_cow_url, R.drawable.ic_cow))
            icons.add(Icon(R.string.credit_icon_hot_dog_description, R.string.credit_icon_hot_dog_url, R.drawable.ic_hot_dog))
            icons.add(Icon(R.string.credit_icon_size_description, R.string.credit_icon_size_url, R.drawable.ic_size))
            icons.add(Icon(R.string.credit_icon_fishes_description, R.string.credit_icon_fishes_url, R.drawable.ic_fishes))
            icons.add(Icon(R.string.credit_icon_sausages_description, R.string.credit_icon_sausages_url, R.drawable.ic_sausages))
            icons.add(Icon(R.string.credit_icon_stall_description, R.string.credit_icon_stall_url, R.drawable.ic_stall))
            icons.add(Icon(R.string.credit_icon_vegetables_description, R.string.credit_icon_vegetables_url, R.drawable.ic_vegetables))
            return icons
        }
    }
}
