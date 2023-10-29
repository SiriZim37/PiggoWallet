package com.example.piggywallet.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val whenLoading = MutableLiveData<Boolean>()
    val whenDataNotFound = MutableLiveData<Boolean>()
    val whenNoInternetConnection = MutableLiveData<Boolean>()
    val whenDataFailure = MutableLiveData<String>()

}