package com.breaking.bad.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.breaking.bad.R
import dagger.hilt.android.AndroidEntryPoint
import net.skoumal.fragmentback.BackFragmentHelper

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        // first ask your fragments to handle back-pressed event
        if(!BackFragmentHelper.fireOnBackPressedEvent(this)) {
            // lets do the default back action if fragments don't consume it
            super.onBackPressed();
        }
    }
}