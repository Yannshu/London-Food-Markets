package com.yannshu.londonfoodmarkets.di.app

import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Named

@Module
class TimeModule {

    companion object {
        val DAY_OF_WEEK_STRING = "EEEE"
        const val NAME_DAY_OF_WEEK_FORMAT = "day_of_week_format"
        const val NAME_DAY_OF_WEEK_STRING = "day_of_week_string"
    }

    @Provides
    @Named(NAME_DAY_OF_WEEK_STRING)
    fun provideDayOfWeek(@Named(NAME_DAY_OF_WEEK_FORMAT) format: SimpleDateFormat): String {
        return format.format(Date()).toLowerCase()
    }

    @Provides
    @Named(NAME_DAY_OF_WEEK_FORMAT)
    fun provideDayOfWeekDateFormat(): SimpleDateFormat {
        return SimpleDateFormat(DAY_OF_WEEK_STRING, Locale.UK)
    }
}