package com.omranic.eyada.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Ad
import com.omranic.eyada.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var ads = MutableLiveData<List<Ad>>()
    private val TAG = "AdViewModel"

    fun getAds(): LiveData<List<Ad>> {
        viewModelScope.launch {
            loadAds()
        }
        return ads
    }

    private suspend fun loadAds() {
        val response = repository.getAds()
        if (response.isSuccessful){
            ads.value = response.body()
        }
    }
}