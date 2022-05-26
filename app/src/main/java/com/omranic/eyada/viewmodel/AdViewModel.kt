package com.omranic.eyada.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Ad
import com.omranic.eyada.repository.Repository
import com.omranic.eyada.util.Resource
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val ads : MutableLiveData<Resource<List<Ad>>> = MutableLiveData()

    init {
        getAds()
    }

    fun getAds() = viewModelScope.launch {
        ads.postValue(Resource.Loading())
        try {
            val response = repository.getAds()
            ads.postValue(handleAdsResponse(response))
        }catch (t: Throwable){
            when(t){
                is IOException -> ads.postValue(Resource.Error("Network Failure"))
                else -> ads.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleAdsResponse(response: Response<List<Ad>>) : Resource<List<Ad>>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

//    private suspend fun safeAdsCall(){
//        ads.postValue(Resource.Loading())
//        try {
//            val response = repository.getAds()
//            ads.postValue(handleAdsResponse(response))
//        }catch (t: Throwable){
//
//        }
//    }
}