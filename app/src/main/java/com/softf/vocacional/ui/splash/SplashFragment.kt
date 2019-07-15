package com.softf.vocacional.ui.splash


import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.softf.vocacional.R
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.databinding.FragmentSplashBinding
import com.softf.vocacional.utils.SingleEventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_splash

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        splashViewModel.repoSelectedLiveData.observe(this, SingleEventObserver { status ->
            findNavController().navigate(R.id.action_splashFragment_to_productsFragment)
            /*if (!status)
                findNavController().navigate(R.id.action_splashFragment_to_repoFragment)
            else
                findNavController().navigate(R.id.action_splashFragment_to_pullRequestsFragment)*/
        })
    }
}
