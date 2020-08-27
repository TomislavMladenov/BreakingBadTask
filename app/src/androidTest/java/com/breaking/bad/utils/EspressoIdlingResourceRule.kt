package com.breaking.bad.utils

import android.util.Log
import androidx.test.espresso.IdlingRegistry
import com.breaking.bad.EspressoIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class EspressoIdlingResourceRule : TestWatcher(){

    private val CLASS_NAME = "EspressoIdlingResourceRule"

    private val idlingResource = EspressoIdlingResource.countingIdlingResource

    override fun finished(description: Description?) {
        Log.d(CLASS_NAME, "FINISHED")
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        Log.d(CLASS_NAME, "STARTING")
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }


}