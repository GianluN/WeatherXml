package com.example.weatherxml

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherxml.model.DataNews
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _newsData = MutableLiveData<List<DataNews>>()
    val newsData: LiveData<List<DataNews>> = _newsData

    fun getNews() {
        viewModelScope.launch {
            val data = repository.getNews()
            _newsData.value = data
        }
    }
}

class MainViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}