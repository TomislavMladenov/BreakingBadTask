package com.breaking.bad.framework.presentation.util

import com.breaking.bad.framework.datasource.model.Character

class SortUtils {

    fun searchByName(name: String, list: List<Character>): List<Character> {
        val sortedList = ArrayList<Character>()
        for(character in list){
            if(character.name.toLowerCase().trim().contains(name.toLowerCase().trim())){
                sortedList.add(character)
            }
        }
        return sortedList
    }

    fun filterBySeasonAppearance(season: Int, list: List<Character>): List<Character> {
        val filteredList = ArrayList<Character>()
        for (i in list.indices){
            for(y in list[i].appearance.indices){
                if(list[i].appearance[y] == season){
                    filteredList.add(list[i])
                }
            }
        }
        return filteredList
    }
}