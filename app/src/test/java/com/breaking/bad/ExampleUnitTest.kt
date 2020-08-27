package com.breaking.bad

import com.breaking.bad.framework.datasource.model.Character
import com.breaking.bad.framework.presentation.util.SortUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private var list: List<Character>? = null
    private val gson = Gson()
    private val sortUtils = SortUtils()

    @Before
    fun generateData(){
        val type = object : TypeToken<List<Character>>() {}.type
        val myFile = ClassLoader.getSystemResource("characters.json").readText()
        list = gson.fromJson<List<Character>>(myFile, type)
    }

    @Test
    fun checkList(){
        assertFalse(list.isNullOrEmpty())
    }

    @Test
    fun checkFilter(){
        val seasonThree = 5
        val filteredList = sortUtils.filterBySeasonAppearance(seasonThree, list!!)

        //Check all from the new list, if they contain season 3 value
        //in their list, if so increment "number"
        var number = 0
        for(character in filteredList){
            for(season in character.appearance){
                if(season == seasonThree){
                    number++
                    break
                }
            }
        }
        //if number == sortedLis.size -> all elements have the correct season
        assertTrue(number == filteredList.size)
    }

    @Test
    fun checkSorting(){
        val name = "Walter"
        val sortedList = sortUtils.searchByName(name, list!!)

        for (character in sortedList){
            if(!character.name.contains(name)){
                assertTrue(character.name.contains(name))
            }
        }
    }
}