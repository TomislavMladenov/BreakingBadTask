package com.breaking.bad.business.data

import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.framework.datasource.CharacterService
import com.breaking.bad.framework.datasource.model.Character

class FakeNetworkDataSourceImpl
constructor(
    private val characterService: CharacterService
): NetworkDataSource {

    override suspend fun getCharacters(): List<Character> {
        return characterService.getCharacters()
    }

}