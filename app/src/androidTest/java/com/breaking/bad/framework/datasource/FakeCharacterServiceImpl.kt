package com.breaking.bad.framework.datasource

import com.breaking.bad.framework.datasource.api.FakeCharacterApi
import com.breaking.bad.framework.datasource.model.Character


class FakeCharacterServiceImpl
constructor(
    private val fakeCharacterApi: FakeCharacterApi
): CharacterService {


    override suspend fun getCharacters(): List<Character> {
        return fakeCharacterApi.getCharacters()
    }
}