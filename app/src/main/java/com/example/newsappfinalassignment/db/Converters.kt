package com.example.newsappfinalassignment.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    val gson = Gson()

    @TypeConverter
    fun listToString(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun stringToList(string: String): List<String> {
        val listType = object : TypeToken<List<String>>(){}.type
        return gson.fromJson(string, listType)
    }
}