package com.softf.vocacional.ui.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softf.vocacional.model.Question
import com.softf.vocacional.model.Response
import com.softf.vocacional.model.repository.AppRepository
import com.softf.vocacional.utils.Constants
import com.softf.vocacional.utils.SingleEvent
import com.softf.vocacional.utils.toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class TestViewModel(private val appRepository: AppRepository): ViewModel() {

    val questionLiveData = MutableLiveData<Response<List<Question>>>()
    val clearQuestionLiveData = MutableLiveData<SingleEvent<Boolean>>()

    val updateQuestionLiveData = MutableLiveData<SingleEvent<Boolean>>()

    fun getTestVocacional() {
        /*if (page == 1) {
            clearPullRequestsLiveData.postValue(SingleEvent(true))
            pullRequestsLiveData.postValue(Response.Loading(true))
        }*/

        clearQuestionLiveData.postValue(SingleEvent(true))
        val handler = CoroutineExceptionHandler { _, throwable ->
            questionLiveData.postValue(Response.Error(throwable))
        }
        viewModelScope.launch(handler) {
            try {
                questionLiveData.postValue(Response.Success(appRepository.getTestVocacional().questions))
            } catch (exception: Exception) {
                //if (page == 1) {
                questionLiveData.postValue(
                    Response.Error(
                        null,appRepository.getQuestionsFromDB()))
                //}else{
                //    throw exception
                //}
            }
        }
    }

    //TODO : VER COMO LLAMAR FUERA DEL HILO PRINCIPAL
    fun updateQuestion(question: Question) {
        val handler = CoroutineExceptionHandler { _, throwable ->
        }
            viewModelScope.launch(handler) {
                try {
                    appRepository.updateQuestion(question)
                } catch (exception: Exception) {
                    Log.e("UPDATE", exception.message)
                }
            }
        }

}