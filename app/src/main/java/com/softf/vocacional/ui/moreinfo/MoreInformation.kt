package com.softf.vocacional.ui.moreinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.softf.vocacional.R
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.databinding.FragmentMoreInformationBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class MoreInformation : BaseFragment<FragmentMoreInformationBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_more_information

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { bundle ->
            getDataBinding().moreInfo = MoreInformationArgs.fromBundle(bundle).testInfo
        }


    }
}
