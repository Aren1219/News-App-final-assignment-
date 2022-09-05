package com.example.newsappfinalassignment.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsappfinalassignment.model.AllNewsList
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.model.Meta
import com.example.newsappfinalassignment.util.Util
import org.junit.Assert.*
import retrofit2.Response

class FakeRepository: Repository {

    private val dataList = Util.previewNewsDataList()
    private val allNewsList = AllNewsList(dataList, Meta(dataList.size, 5, 1, 5))

    private val savedNewsList: MutableList<Data> = mutableListOf()
    private val _savedNews: MutableLiveData<List<Data>> = MutableLiveData(savedNewsList)

    override suspend fun getNews(page: Int, publishedBefore: String?): Response<AllNewsList> =
        Response.success(allNewsList)

    override fun getSavedNews(): LiveData<List<Data>> = _savedNews

    override suspend fun saveNews(data: Data) {
        savedNewsList.add(data)
    }

    override suspend fun deleteNews(data: Data) {
        savedNewsList.remove(data)
    }

}