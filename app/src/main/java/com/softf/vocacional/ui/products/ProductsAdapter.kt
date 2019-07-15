package com.softf.vocacional.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softf.vocacional.databinding.ProductsListItemBinding
import com.softf.vocacional.model.Product

class ProductsAdapter(private val onProductsClickedListener: OnProductsClickedListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val productList: MutableList<Product> = ArrayList()

    inner class ProductsViewHolder(private val binding: ProductsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {

            binding.apply {
                setProduct(product)
                onProductsClickedListener = this@ProductsAdapter.onProductsClickedListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun addProducts(pullRequestList: List<Product>) {
        this.productList.addAll(pullRequestList)
        notifyDataSetChanged()
    }

    fun clearProducts() {
        this.productList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productList[position])
    }


    interface OnProductsClickedListener {
        fun onProductsClicked(product: Product, button: Int)
    }

}

