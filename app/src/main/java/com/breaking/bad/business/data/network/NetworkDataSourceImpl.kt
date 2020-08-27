package com.breaking.bad.business.data.network

import com.breaking.bad.framework.datasource.CharacterService
import com.breaking.bad.framework.datasource.model.Character


class NetworkDataSourceImpl
constructor(
    private val characterService: CharacterService
): NetworkDataSource {

    override suspend fun getCharacters(): List<Character> {
        return characterService.getCharacters()
    }

}