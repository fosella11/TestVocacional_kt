package com.softf.vocacional.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softf.vocacional.model.Response
import com.softf.vocacional.model.Result
import com.softf.vocacional.model.repository.AppRepository
import com.softf.vocacional.utils.ProcessTests
import com.softf.vocacional.utils.SingleEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ResultTestViewModel(private val appRepository: AppRepository) : ViewModel() {
    val resultsLiveData = MutableLiveData<Response<List<Result>>>()
    val clearResultsLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val resultNotReady = MutableLiveData<SingleEvent<Boolean>>()
    var resultsList = emptyList<Result>()

    var resultResponses = ArrayList<String>()

    fun getResultTest(typeTest: String) {
        clearResultsLiveData.postValue(SingleEvent(true))
        val handler = CoroutineExceptionHandler { _, throwable ->
            resultsLiveData.postValue(Response.Error(throwable))
        }
        viewModelScope.launch(handler) {
            try {
                if (typeTest.equals("1")) {
                    val questions = appRepository.getQuestionsFromDB().filter {
                        it.testId == 1 && it.isResponded
                    }
                    // 0 .. 100
                    if (questions.size != 97) {
                        resultNotReady.postValue(SingleEvent(true))
                        return@launch
                    }
                    resultResponses.add(ProcessTests.getInstance().testOneGetAptitude(questions))
                    resultResponses.add(ProcessTests.getInstance().testOneGetInterest(questions))
                    resultsList = createListTestOne(resultResponses)
                }
                if (typeTest.equals("2")) {
                    val questions = appRepository.getQuestionsFromDB().filter {
                        it.testId == 2 && it.isResponded
                    }
                    // 0 .. 100
                    if (questions.size != 80) {
                        resultNotReady.postValue(SingleEvent(true))
                        return@launch
                    }
                    val results = ProcessTests.getInstance().testTwoGetAreas(questions)
                    resultsList = createListTestTwo(results)
                }
                resultsLiveData.postValue(Response.Success(resultsList))
            } catch (exception: Exception) {
                //if (page == 1) {
                resultsLiveData.postValue(Response.Error(exception, resultsList))
                //}else{
                //    throw exception
                //}
            }
        }
    }

    private fun createListTestOne(arrayList: ArrayList<String>): List<Result> {
        var results = ArrayList<Result>()
        val r1 = Result(arrayList[0], "Intereses")
        val r2 = Result(arrayList[1], "Aptitudes")
        results.add(r1)
        results.add(r2)
        return results.toMutableList()
    }

    private fun createListTestTwo(arrayList: ArrayList<String>): List<Result> {
        var results = ArrayList<Result>()
        val r1 = Result(arrayList[0], "Primer opción")
        val r2 = Result(arrayList[1], "Segunda opción")
        results.add(r1)
        results.add(r2)
        return results
    }
}