package com.example.newsappfinalassignment.repo

import androidx.lifecycle.LiveData
import com.example.newsappfinalassignment.api.Api
import com.example.newsappfinalassignment.db.NewsDao
import com.example.newsappfinalassignment.model.AllNewsList
import com.example.newsappfinalassignment.model.Data
import retrofit2.Response
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val api: Api,
    val newsDao: NewsDao
): Repository {


    override suspend fun getNews(
        page: Int,
        publishedBefore: String
    ): Response<AllNewsList> =
        api.getAllNews(page = page, publishedBefore = publishedBefore)

    override fun getSavedNews(): LiveData<List<Data>> = newsDao.getAllNews()

    override suspend fun saveNews(data: Data) {
        newsDao.insertNews(data)
    }

    override suspend fun deleteNews(data: Data) {
        deleteNews(data)
    }
}