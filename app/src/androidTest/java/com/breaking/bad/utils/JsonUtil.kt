package com.breaking.bad.utils

import android.content.Context
import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * Class for parsing data from fake data assets
 */
class JsonUtil
@Inject
constructor(
    private val appContext: Context
){

    private val CLASS_NAME = "JsonUtil"

    fun readJSONFromAsset(fileName: String): String? {
        var json: String? = null
        json = try {
            val inputStream: InputStream = (appContext.assets as AssetManager).open(fileName)
            inputStream.bufferedReader().use{it.readText()}
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}