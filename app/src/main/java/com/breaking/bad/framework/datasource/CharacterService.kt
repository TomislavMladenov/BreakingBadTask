package com.breaking.bad.framework.datasource

import com.breaking.bad.framework.datasource.model.Character

interface CharacterService {

    suspend fun getCharacters(): List<Character>
}