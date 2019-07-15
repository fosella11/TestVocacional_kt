package com.softf.vocacional.ui.webview


import android.os.Bundle
import com.softf.vocacional.R
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.custom.LoadingDialog
import com.softf.vocacional.databinding.FragmentWebViewBinding
import com.softf.vocacional.utils.doOnProgressCompleted
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    private val loadingDialog by lazy {
        LoadingDialog(requireActivity()).apply {
            setCancelable(false)
            lifecycle.addObserver(this)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_web_view

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { bundle ->
            getDataBinding().url = WebViewFragmentArgs.fromBundle(bundle).url
        }

        loadingDialog.toggle(true)
        webView.doOnProgressCompleted { loadingDialog.toggle(false) }
    }
}
