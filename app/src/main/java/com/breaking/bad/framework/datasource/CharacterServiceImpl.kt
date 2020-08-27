package com.breaking.bad.framework.datasource

import com.breaking.bad.framework.datasource.api.CharactersApi
import com.breaking.bad.framework.datasource.model.Character
import javax.inject.Singleton

@Singleton
class CharacterServiceImpl
constructor(
    private val charactersApi: CharactersApi
): CharacterService {

    override suspend fun getCharacters(): List<Character> {
        return charactersApi.getCharacters()
    }

}