package com.softf.vocacional.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softf.vocacional.R
import com.softf.vocacional.base.BaseFragment
import com.softf.vocacional.custom.LoadingDialog
import com.softf.vocacional.databinding.FragmentProductsBinding
import com.softf.vocacional.model.Product
import com.softf.vocacional.model.Response
import com.softf.vocacional.utils.EndlessRecyclerOnScrollListener
import com.softf.vocacional.utils.SingleEventObserver
import com.softf.vocacional.utils.getErrorMessage
import com.softf.vocacional.utils.toast
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : BaseFragment<FragmentProductsBinding>(),
    ProductsAdapter.OnProductsClickedListener {

    //VER ESTO KOIN
    private val viewModel by viewModel<ProductsViewModel>()


    private val productsAdapter by lazy { ProductsAdapter(this) }
    private val loadingDialog by lazy {
        LoadingDialog(requireActivity()).apply {
            lifecycle.addObserver(this)
        }
    }

    private val endLessRecyclerView by lazy {
        object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(current_page: Int) {
                //viewModel.getTestVocacional()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_products

    override fun onProductsClicked(product: Product, button: Int) {
        when(button){
            0 -> moreInfo(product)
            1 -> runTest(product)
            2 -> resultTest(product)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getDataBinding().productsAdapter = productsAdapter
        getDataBinding().viewModel = viewModel

        viewModel.clearProductsLiveData.observe(this, SingleEventObserver { status ->
            if (status) {
                productsAdapter.clearProducts()
                endLessRecyclerView.reset()
            }
        })

        viewModel.productsLiveData.observe(this, Observer { response ->

            when(response) {
                is Response.Loading -> {
                    if(!productsRefresh.isRefreshing){
                        loadingDialog.toggle(response.status)
                    }
                }
                is Response.Success -> {
                    productsAdapter.clearProducts()
                    productsAdapter.addProducts(response.data)
                    productsRefresh.isRefreshing = false
                    loadingDialog.toggle(false)
                }
                is Response.Error -> {
                    response.throwable?.getErrorMessage(requireContext())?.toast(requireContext())
                    response.data?.let { productList ->
                        productsAdapter.clearProducts()
                        productsAdapter.addProducts(productList)
                    }
                    productsRefresh.isRefreshing = false
                    loadingDialog.toggle(false)
                }
            }

        })

        //productsRecyclerView.addOnScrollListener(endLessRecyclerView)
        setHasOptionsMenu(true)
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

    private fun moreInfo(product: Product) {
        if (findNavController().currentDestination?.id == R.id.productsFragment) {
            findNavController().navigate(
                ProductsFragmentDirections.actionProductsFragmentToMoreInformation(
                    product.fullInfo
                )
            )
        }
    }

    private fun runTest(product: Product) {
        //El uid de Product es el test_id de la lista de pregutnas, El test
        //
        if (product.isFree) {
            if (findNavController().currentDestination?.id == R.id.productsFragment) {
                findNavController().navigate(
                    ProductsFragmentDirections.actionProductsFragmentToTestFragment(
                        product.uid
                    )
                )
            }
        } else {
            if (findNavController().currentDestination?.id == R.id.productsFragment) {
                findNavController().navigate(
                    ProductsFragmentDirections.actionProductsFragmentToWebViewFragment(
                        product.urlInfo
                    )
                )
            }
        }
    }

    private fun resultTest(product: Product){
        if (findNavController().currentDestination?.id == R.id.productsFragment) {
            findNavController().navigate(
                ProductsFragmentDirections.actionProductsFragmentToResultTest(product.uid.toString())
            )
        }
    }
}