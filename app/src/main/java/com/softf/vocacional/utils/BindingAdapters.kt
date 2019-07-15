package com.softf.vocacional.utils

import android.content.res.Resources.NotFoundException
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.softf.vocacional.R
import com.softf.vocacional.ui.products.ProductsAdapter
import com.softf.vocacional.ui.test.TestAdapter

/**
 * An adapter to bind errors to EditText
 */

@BindingAdapter("error")
fun EditText.setError(error: Int) {
    try {
        setError(context.getString(error))
        requestFocus()
    } catch (e: NotFoundException) {
        setError(null)
        clearFocus()
    }

}

@BindingAdapter("questionAdapter")
fun RecyclerView.setQuestionAdapter(adapter: TestAdapter?) {
    layoutManager = LinearLayoutManager(context)
    setAdapter(adapter)
}

@BindingAdapter("productsAdapter")
fun RecyclerView.setProductsAdapter(adapter: ProductsAdapter?) {
    layoutManager = LinearLayoutManager(context)
    setAdapter(adapter)
}

@BindingAdapter("onRefresh")
inline fun SwipeRefreshLayout.setOnRefreshListener(crossinline onRefresh: () -> Unit) {
    setOnRefreshListener { onRefresh() }
}

@BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
fun setImageByUrl(imageView: ImageView , imageUrl: String, defaultImage: String?) {
    var default: Int = R.drawable.test1
        defaultImage.let {
            when(defaultImage){
                "one" -> default = R.drawable.test1
                "two" -> default = R.drawable.test2
                "three" -> default = R.drawable.testprofesional
            }
        }
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(
        imageView.context).load(
        imageUrl).placeholder(default).apply(
        requestOptions)
        .into(
        imageView)
}

@BindingAdapter("isFree")
fun ImageView.setState(isFree: Boolean) {
    ViewCompat.setBackground(
        this,
        if (isFree)
            ContextCompat.getDrawable(context, R.drawable.free)
        else ContextCompat.getDrawable(context, R.drawable.pago)
    )
}

@BindingAdapter("requestState")
fun TextView.setState(requestState: String) {
    text = requestState
    ViewCompat.setBackground(
        this,
        if (requestState == context.getString(R.string.open))
            ContextCompat.getDrawable(context, R.drawable.red_rect_filled)
        else ContextCompat.getDrawable(context, R.drawable.blue_rect_filled)
    )
}

@BindingAdapter(value = ["requestState", "requestNumber", "createdDate", "updatedDate"])
fun TextView.setRequestDate(
    requestState: String, requestNumber: Long,
    createdDate: String, updatedDate: String
) {
    text = if (requestState == context.getString(R.string.open)) {
        "#$requestNumber opened on ${createdDate.getFormattedDate()}"
    } else {
        "#$requestNumber was merged on ${updatedDate.getFormattedDate()}"
    }
}

@BindingAdapter("url")
fun WebView.loadRepoUrl(url: String) {
    run { loadUrl(url) }
}