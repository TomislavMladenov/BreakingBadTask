package com.breaking.bad.utils

import android.widget.ImageView
import com.breaking.bad.framework.presentation.util.GlideManager
import javax.inject.Inject
import javax.inject.Singleton


class FakeGlideRequestManager
constructor(): GlideManager {

    override fun setImage(imageUrl: String, imageView: ImageView){
        // does nothing
    }
}