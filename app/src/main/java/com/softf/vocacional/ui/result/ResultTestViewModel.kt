package com.softf.vocacional.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softf.vocacional.R
import com.softf.vocacional.model.Response
import com.softf.vocacional.model.Result
import com.softf.vocacional.model.repository.AppRepository
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
        val results = ArrayList<Result>()
        val r1 = Result(getShareImage(arrayList[0]), arrayList[0], "Intereses")
        val r2 = Result(getShareImage(arrayList[1]), arrayList[1], "Aptitudes")
        results.add(r1)
        results.add(r2)
        return results.toMutableList()
    }

    private fun createListTestTwo(arrayList: ArrayList<String>): List<Result> {
        val results = ArrayList<Result>()
        val r1 = Result(getShareImage(arrayList[0]),arrayList[0], "Primer opción")
        val r2 = Result(getShareImage(arrayList[1]),arrayList[1], "Segunda opción")
        results.add(r1)
        results.add(r2)
        return results
    }

    private fun getShareImage(imageResult: String): Int{
        var image = R.drawable.testprofesional
        when (imageResult) {
            "A1" -> image = R.drawable.ac_share
            "A2" -> image = R.drawable.ah_share
            "A3" -> image = R.drawable.aa_share
            "A4" -> image = R.drawable.aas_share
            "A5" -> image = R.drawable.ai_share
            "A6" -> image = R.drawable.ad_share
            "A7" -> image = R.drawable.ae_share
            "I1" -> image = R.drawable.ic_share
            "I2" -> image = R.drawable.ih_share
            "I3" -> image = R.drawable.ia_share
            "I4" -> image = R.drawable.iis_share
            "I5" -> image = R.drawable.ii_share
            "I6" -> image = R.drawable.id_share
            "I7" -> image = R.drawable.ie_share
            //TEST 2
            "AA1" -> image = R.drawable.ii_a_one
            "AA2" -> image = R.drawable.ii_a_two
            "AA3" -> image = R.drawable.ii_a_three
            "AA4" -> image = R.drawable.ii_a_four
            "AA5" -> image = R.drawable.ii_a_five
            else -> image = R.drawable.testprofesional
        }
        return image 
    }
}