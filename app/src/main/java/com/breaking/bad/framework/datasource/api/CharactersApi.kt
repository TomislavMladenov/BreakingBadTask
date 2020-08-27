package com.breaking.bad.framework.datasource.api

import com.breaking.bad.framework.datasource.model.Character
import retrofit2.http.GET

interface CharactersApi {

    companion object {
        const val GET_CHARACTERS = "api/characters"
    }

    @GET(GET_CHARACTERS)
    suspend fun getCharacters(): List<Character>
}