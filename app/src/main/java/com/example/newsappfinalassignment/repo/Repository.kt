package com.example.newsappfinalassignment.repo

import androidx.lifecycle.LiveData
import com.example.newsappfinalassignment.model.AllNewsList
import com.example.newsappfinalassignment.model.Data
import retrofit2.Response

interface Repository {

    suspend fun getNews(page: Int, publishedBefore: String?): Response<AllNewsList>

    fun getSavedNews(): LiveData<List<Data>>

    suspend fun saveNews(data: Data)

    suspend fun deleteNews(data: Data)
}