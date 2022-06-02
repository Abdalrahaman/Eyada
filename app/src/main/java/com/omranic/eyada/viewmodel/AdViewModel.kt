package com.omranic.eyada.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Ad
import com.omranic.eyada.repository.Repository
import com.omranic.eyada.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val ads : MutableLiveData<Resource<List<Ad>>> = MutableLiveData()

    fun getAds() = viewModelScope.launch {
        ads.postValue(Resource.Loading())
        try {
            val response = repository.getAds()
            ads.postValue(handleAdsResponse(response))
            insertAdsToDB(response.body()!!)
        }catch (t: Throwable){
            when(t){
                is IOException -> ads.postValue(Resource.Error("Network Failure", getAdsFromDB()))
                else -> ads.postValue(Resource.Error(t.message.toString())) //"Conversion Error"
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

    private fun insertAdsToDB(ads: List<Ad>){
        repository.insertAdsToDB(ads)
    }

    private fun getAdsFromDB(): List<Ad> = repository.getAdsFromDB()
}