package com.breaking.bad.business.data.network

import com.breaking.bad.framework.datasource.model.Character

interface NetworkDataSource {

    suspend fun getCharacters(): List<Character>
}