package com.example.newsappfinalassignment.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappfinalassignment.model.Data
import com.example.newsappfinalassignment.repo.Repository
import com.example.newsappfinalassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
): ViewModel() {

    private val _newsList: MutableLiveData<Resource<List<Data>>> =
        MutableLiveData(Resource.Loading())
    val newsList: LiveData<Resource<List<Data>>> = _newsList

    var newsListPage by mutableStateOf(1)
    var loadedPage = 0

    val currentDate: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date())

    private val savedList: LiveData<List<Data>> = repository.getSavedNews()

    init {
        getNewsList()
    }
    
    fun getNewsList() = viewModelScope.launch(Dispatchers.IO) {
        _newsList.postValue(Resource.Loading())
        val response = repository.getNews(page = loadedPage + 1, currentDate)
        if (response.isSuccessful){
            _newsList.postValue(Resource.Success(response.body()!!.data))
            loadedPage ++
        } else {
            _newsList.postValue(Resource.Error(response.message()))
        }
    }

    fun saveNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveNews(data)
    }

    fun deleteNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNews(data)
    }
}