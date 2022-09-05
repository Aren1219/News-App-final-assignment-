package com.example.newsappfinalassignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.repo.Repository
import com.example.newsappfinalassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
): ViewModel() {

    private val _newsList: MutableLiveData<Resource<List<Data>>> = MutableLiveData()
    val newsList: LiveData<Resource<List<Data>>> = _newsList

    private var loadedPage = 0

    private var currentDateTime: String? = null

    val savedList: LiveData<List<Data>> = repository.getSavedNews()

    init {
        getNewsList()
    }

    fun getNewsList() = viewModelScope.launch(Dispatchers.IO) {
        if (_newsList.value is Resource.Loading<*>) return@launch
        _newsList.postValue(Resource.Loading(_newsList.value?.data))
        try {
            val response = repository.getNews(page = loadedPage + 1, currentDateTime)
            if (response.isSuccessful){
                _newsList.postValue(Resource.Success(
                    _newsList.value?.data?.plus(response.body()!!.data) ?: response.body()!!.data)
                )
                if (loadedPage == 0) currentDateTime = response.body()!!.data[0].publishedAt.take(19)
                loadedPage ++
            } else {
                _newsList.postValue(Resource.Error(response.message()))
            }
        } catch (e: Exception){
            _newsList.postValue(Resource.Error("ERROR: could not load online news"))
        }
    }

    fun getNewsUUID(uuid: String): Data? = _newsList.value?.data?.find { it.uuid == uuid }
        ?: savedList.value?.find { it.uuid == uuid }


    fun saveNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveNews(data)
    }

    fun deleteNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNews(data)
    }
}