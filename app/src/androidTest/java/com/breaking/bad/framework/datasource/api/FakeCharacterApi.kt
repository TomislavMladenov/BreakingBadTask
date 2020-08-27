package com.breaking.bad.framework.datasource.api

import com.breaking.bad.framework.datasource.model.Character
import com.breaking.bad.utils.Constants
import com.breaking.bad.utils.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay


class FakeCharacterApi
constructor(
    private val jsonUtil: JsonUtil,
): CharactersApi {

    var charactersJsonFileName: String = Constants.CHARACTER_DATA_FILENAME
    var networkDelay = 0L

    override suspend fun getCharacters(): List<Character> {
        val rawJson = jsonUtil.readJSONFromAsset(charactersJsonFileName)
        val characters = Gson().fromJson<List<Character>>(
            rawJson,
            object : TypeToken<List<Character>>() {}.type
        )
        delay(networkDelay) //If we want to simulate all scenarios (this value should be set from outside)
        return characters
    }


}