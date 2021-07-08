package com.example.health2u.ui.testcenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health2u.model.*
import com.example.health2u.repo.Repository
import com.example.health2u.utils.Utils
import kotlinx.coroutines.launch

class TestCenterViewModel(private val repository: Repository) : ViewModel() {

    private var _dialog = MutableLiveData<String>()
    var dialog: LiveData<String> = _dialog

    private var _centerslist = MutableLiveData<ArrayList<Centers>>()
    var centerslist: LiveData<ArrayList<Centers>> = _centerslist


    fun getCentersList(latlong: String) {
        viewModelScope.launch {
            val centersresult =
                repository.getTestCenters(Utils.test_center_api_key, "Covid", latlong, "20")

            if (centersresult is ServiceResult.Error) {
                _dialog.value = centersresult.error.errorMessages
            } else {
                val itemData =
                    ((centersresult as? ServiceResult.Success)?.data as? CenterResponse)?.articles
                _centerslist.value = itemData as ArrayList<Centers>

            }
        }
    }
}