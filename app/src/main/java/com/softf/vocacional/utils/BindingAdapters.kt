package com.softf.vocacional.utils

import android.annotation.SuppressLint
import android.content.res.Resources.NotFoundException
import android.graphics.Color
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
import com.softf.vocacional.ui.result.ResultsAdapter
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

@BindingAdapter("resultAdapter")
fun RecyclerView.setResultAdapter(adapter: ResultsAdapter?) {
    layoutManager = LinearLayoutManager(context)
    setAdapter(adapter)
}

@BindingAdapter("onRefresh")
inline fun SwipeRefreshLayout.setOnRefreshListener(crossinline onRefresh: () -> Unit) {
    setOnRefreshListener { onRefresh() }
}

@BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
fun setImageByUrl(imageView: ImageView, imageUrl: String, defaultImage: String?) {
    var default: Int = R.drawable.test1
    defaultImage.let {
        when (defaultImage) {
            "one" -> default = R.drawable.test1
            "two" -> default = R.drawable.test2
            "three" -> default = R.drawable.testprofesional
        }
    }
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(
        imageView.context
    ).load(
        imageUrl
    ).placeholder(default).apply(
        requestOptions
    )
        .into(
            imageView
        )
}

@BindingAdapter(value = ["imageResult"], requireAll = false)
fun setImageByUrl(imageView: ImageView, imageResult: String) {
    var image = R.drawable.testprofesional
    when (imageResult) {
        "A1" -> image = R.drawable.ac
        "A2" -> image = R.drawable.ah
        "A3" -> image = R.drawable.aa
        "A4" -> image = R.drawable.aas
        "A5" -> image = R.drawable.ai
        "A6" -> image = R.drawable.ad
        "A7" -> image = R.drawable.ae
        "I1" -> image = R.drawable.ic
        "I2" -> image = R.drawable.ih
        "I3" -> image = R.drawable.ia
        "I4" -> image = R.drawable.iis
        "I5" -> image = R.drawable.ii
        "I6" -> image = R.drawable.id
        "I7" -> image = R.drawable.ie
        //TEST 2
        "AA1" -> image = R.drawable.ii_a_one
        "AA2" -> image = R.drawable.ii_a_two
        "AA3" -> image = R.drawable.ii_a_three
        "AA4" -> image = R.drawable.ii_a_four
        "AA5" -> image = R.drawable.ii_a_five
        else -> image = R.drawable.testprofesional
    }
    imageView.setImageResource(image)
}

@SuppressLint("ResourceAsColor")
@BindingAdapter(value = ["setColorYes", "responded"], requireAll = false)
fun setColorYes(textView: TextView, setColorYes: Boolean, responded: Boolean) {
    if(responded) {
        if (setColorYes) {
            ViewCompat.setBackground(
                textView,
                ContextCompat.getDrawable(textView.context, R.drawable.blue_rect_filled)
            )
            textView.setTextColor(Color.WHITE)
        } else {
            ViewCompat.setBackground(
                textView,
                ContextCompat.getDrawable(textView.context, R.drawable.transparent_rect_filled)
            )
            textView.setTextColor(R.color.blue)
        }
    }else{
        ViewCompat.setBackground(
            textView,
            ContextCompat.getDrawable(textView.context, R.drawable.transparent_rect_filled)
        )
        textView.setTextColor(R.color.blue)
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter(value = ["setColorNo", "responded"], requireAll = false)
fun setColorNo(textView: TextView, setColorNo: Boolean, responded: Boolean) {
    if(responded) {
        if (!setColorNo) {
            ViewCompat.setBackground(
                textView,
                ContextCompat.getDrawable(textView.context, R.drawable.blue_rect_filled)
            )
            textView.setTextColor(Color.WHITE)
        } else {
            ViewCompat.setBackground(
                textView,
                ContextCompat.getDrawable(textView.context, R.drawable.transparent_rect_filled)
            )
            textView.setTextColor(R.color.blue)
        }
    }else{
        ViewCompat.setBackground(
            textView,
            ContextCompat.getDrawable(textView.context, R.drawable.transparent_rect_filled)
        )
        textView.setTextColor(R.color.blue)
    }
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