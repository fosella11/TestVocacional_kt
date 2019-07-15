package com.softf.vocacional.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softf.vocacional.model.Product
import com.softf.vocacional.model.Question
import com.softf.vocacional.model.Response
import com.softf.vocacional.model.repository.AppRepository
import com.softf.vocacional.utils.SingleEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProductsViewModel(private val appRepository: AppRepository) : ViewModel() {

    val productsLiveData = MutableLiveData<Response<List<Product>>>()
    val clearProductsLiveData = MutableLiveData<SingleEvent<Boolean>>()

    init {
        getTestVocacional()
    }


    fun getTestVocacional() {
        /*if (page == 1) {
            clearPullRequestsLiveData.postValue(SingleEvent(true))
            pullRequestsLiveData.postValue(Response.Loading(true))
        }*/

        clearProductsLiveData.postValue(SingleEvent(true))
        val handler = CoroutineExceptionHandler { _, throwable ->
            productsLiveData.postValue(Response.Error(throwable))
        }
        viewModelScope.launch(handler) {
            try {
                productsLiveData.postValue(Response.Success(appRepository.getTestVocacional().products))
            } catch (exception: Exception) {
                //if (page == 1) {
                productsLiveData.postValue(Response.Error(exception,appRepository.getProductsFromDB()))
                //}else{
                //    throw exception
                //}
            }
        }
    }
}