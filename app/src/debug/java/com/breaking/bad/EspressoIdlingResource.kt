package com.breaking.bad

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource


object EspressoIdlingResource {

    private val CLASS_NAME = "EspressoIdlingResource"

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        Log.d(CLASS_NAME, "INCREMENTING.")
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            Log.d(CLASS_NAME, "DECREMENTING.")
            countingIdlingResource.decrement()
        }
    }

    fun clear() {
        if (!countingIdlingResource.isIdleNow) {
            decrement()
            clear()
        }
    }
}








