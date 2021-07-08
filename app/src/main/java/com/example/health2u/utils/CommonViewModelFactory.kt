package com.example.health2u.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.health2u.repo.Repository
import com.example.health2u.ui.NewsViewModel
import com.example.health2u.ui.testcenter.TestCenterViewModel
import java.lang.IllegalArgumentException


class CommonViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(TestCenterViewModel::class.java)) {
            return TestCenterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }

}