package com.softf.vocacional.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softf.vocacional.databinding.ProductsListItemBinding
import com.softf.vocacional.databinding.ResultsListItemBinding
import com.softf.vocacional.model.Product
import com.softf.vocacional.model.Result

class ResultsAdapter(private val onResultsClickedListener: OnResultsClickedListener) :
    RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>() {

    private val resultList: MutableList<Result> = ArrayList()

    inner class ResultsViewHolder(private val binding: ResultsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {

            binding.apply {
                setResult(result)
                onResultsClickedListener = this@ResultsAdapter.onResultsClickedListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            ResultsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addResults(resultList: List<Result>) {
        this.resultList.addAll(resultList)
        notifyDataSetChanged()
    }

    fun clearResults() {
        this.resultList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = resultList.size

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(resultList[position])
    }


    interface OnResultsClickedListener {
        fun onResultsShare(result: Result)
        fun onResultsRate(result: Result)
    }

}

