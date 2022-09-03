package com.example.newsappfinalassignment.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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

    private val _newsList: MutableLiveData<Resource<List<Data>>> = MutableLiveData()
    val newsList: LiveData<Resource<List<Data>>> = _newsList

    private var loadedPage = 0

    private val currentDate: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date())

    private val savedList: LiveData<List<Data>> = repository.getSavedNews()

    init {
        getNewsList()
    }

    fun getNewsList() = viewModelScope.launch(Dispatchers.IO) {
        if (_newsList.value is Resource.Loading<*>) return@launch
        _newsList.postValue(Resource.Loading(_newsList.value?.data))
        val response = repository.getNews(page = loadedPage + 1, currentDate)
        if (response.isSuccessful){
            _newsList.postValue(Resource.Success(
                _newsList.value?.data?.plus(response.body()!!.data) ?: response.body()!!.data)
            )
            loadedPage ++
        } else {
            _newsList.postValue(Resource.Error(response.message()))
        }
    }

    fun getNewsUUID(uuid: String): Data? = _newsList.value?.data?.find { it.uuid == uuid }


    fun saveNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveNews(data)
    }

    fun deleteNews(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNews(data)
    }
}