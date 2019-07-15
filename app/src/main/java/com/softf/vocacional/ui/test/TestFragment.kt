package com.softf.vocacional.ui.test


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.softf.vocacional.R
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.custom.LoadingDialog
import com.softf.vocacional.databinding.FragmentQuestionsBinding
import com.softf.vocacional.model.Question
import com.softf.vocacional.model.Response
import com.softf.vocacional.utils.SingleEventObserver
import com.softf.vocacional.utils.getErrorMessage
import com.softf.vocacional.utils.toast
import kotlinx.android.synthetic.main.fragment_questions.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : BaseFragment<FragmentQuestionsBinding>(), TestAdapter.OnQuestionsClickedListener {

    private var testIdTemp = 0
    private val viewModel by viewModel<TestViewModel>()
    private val questionAdapter by lazy { TestAdapter(this) }
    private val loadingDialog by lazy {
        LoadingDialog(requireActivity()).apply {
            lifecycle.addObserver(this)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_questions

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getDataBinding().questionsAdapter = questionAdapter
        getDataBinding().viewModel = viewModel


        arguments?.let { bundle ->
            testIdTemp = TestFragmentArgs.fromBundle(bundle).testId
            getDataBinding().viewModel?.getTestVocacional()
        }

        viewModel.clearQuestionLiveData.observe(
            this,
            SingleEventObserver{
                status ->
                if(status) {
                    questionAdapter.clearQuestions()
                }
            }
        )

        viewModel.questionLiveData.observe(
            this,
            Observer {
                response ->
                when(response){

                    is Response.Loading -> {
                        if(!questionsRefresh.isRefreshing){
                            loadingDialog.toggle(response.status)
                        }
                    }
                    is Response.Success -> {
                        questionAdapter.clearQuestions()
                        questionAdapter.addQuestions(response.data, testIdTemp)
                        questionsRefresh.isRefreshing = false
                        loadingDialog.toggle(false)
                    }
                    is Response.Error -> {
                        response.throwable?.getErrorMessage(requireContext())?.toast(requireContext())
                        response.data?.let {
                            questionList ->
                            questionAdapter.clearQuestions()
                            questionAdapter.addQuestions(response.data, testIdTemp)
                        }
                        questionsRefresh.isRefreshing = false
                        loadingDialog.toggle(false)
                    }
                }
            }
        )

        setHasOptionsMenu(true)
    } // onActivityCreated

    override fun onQuestionClickedListener(question: Question, button: Int) {
        when(button){
            //NO - NO INTERESTED
            0 -> {
                question.isResponded = true
                question.response = false
                viewModel.updateQuestion(question)
            }
            //SI - INTERESTED
            1 -> {
                question.isResponded = true
                question.response = true
                viewModel.updateQuestion(question)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_products, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if (item.itemId == R.id.actionChangeRepository)

        //TODO Add menu options

        //viewModel.changeRepository()
        return super.onOptionsItemSelected(item)
    }
}
