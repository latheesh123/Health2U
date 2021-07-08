package com.example.health2u.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health2u.model.NewsResponse
import com.example.health2u.model.ServiceResult
import com.example.health2u.model.Sources
import com.example.health2u.repo.Repository
import com.example.health2u.utils.Utils
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {
    // TODO: Implement the ViewModel


    private var _dialog = MutableLiveData<String>()
    var dialog: LiveData<String> = _dialog

    private var _newslist = MutableLiveData<ArrayList<Sources>>()
    var newslist: LiveData<ArrayList<Sources>> = _newslist


    fun getNewsList()
    {
        viewModelScope.launch {
            val newsResult=repository.getNewsArticles("us","health",Utils.api_key)

            if (newsResult is ServiceResult.Error) {
                _dialog.value = newsResult.error.errorMessages
            } else {
                val itemData =
                    ((newsResult as? ServiceResult.Success)?.data as? NewsResponse)?.articles
                _newslist.value = itemData as ArrayList<Sources>

            }
        }
    }

}

