package com.softf.vocacional.ui.result

import android.content.ContentResolver
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.custom.LoadingDialog
import com.softf.vocacional.databinding.FragmentResultTestBinding
import com.softf.vocacional.model.Response
import com.softf.vocacional.model.Result
import com.softf.vocacional.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri
import com.softf.vocacional.R


class ResultTest : BaseFragment<FragmentResultTestBinding>(),
    ResultsAdapter.OnResultsClickedListener {

    private val resultsAdapter by lazy { ResultsAdapter(this) }
    private val viewModel by viewModel<ResultTestViewModel>()
    private var test = ""
    private val loadingDialog by lazy {
        LoadingDialog(requireActivity()).apply {
            lifecycle.addObserver(this)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_result_test

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { bundle ->
            test = ResultTestArgs.fromBundle(bundle).result
        }

        getDataBinding().resultsAdapter = resultsAdapter
        getDataBinding().viewModel = viewModel

        viewModel.getResultTest(test)

        viewModel.clearResultsLiveData.observe(this, SingleEventObserver { status ->
            if (status) {
                resultsAdapter.clearResults()
            }
        })

        viewModel.resultNotReady.observe(this, SingleEventObserver { status ->
            if (status) {
                Toast.makeText(requireContext(), getString(R.string.error_require_questions), Toast.LENGTH_SHORT).show()
                if (findNavController().currentDestination?.id == R.id.resultTest) {
                    findNavController().popBackStack()
                }
            }
        })

        viewModel.resultsLiveData.observe(this, Observer { response ->

            when (response) {
                is Response.Loading -> {
                    loadingDialog.toggle(response.status)
                }
                is Response.Success -> {
                    resultsAdapter.clearResults()
                    resultsAdapter.addResults(response.data)
                    loadingDialog.toggle(false)
                }
                is Response.Error -> {
                    response.throwable?.getErrorMessage(requireContext())?.toast(requireContext())
                    response.data?.let { productList ->
                        resultsAdapter.clearResults()
                        resultsAdapter.addResults(productList)
                    }
                    loadingDialog.toggle(false)
                }
            }

        })

        setHasOptionsMenu(true)
    }

    override fun onResultsShare(result: Result) {
        val resourceId = result.imageShare // r.mipmap.yourmipmap; R.drawable.yourdrawable
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(resourceId!!))
            .appendPath(resources.getResourceTypeName(resourceId))
            .appendPath(resources.getResourceEntryName(resourceId))
            .build()
        shareImageUri(uri)
    }

    override fun onResultsRate(result: Result) {
        Log.e("FEDE", "FEDE RATE")
    }

    private fun shareImageUri(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}
