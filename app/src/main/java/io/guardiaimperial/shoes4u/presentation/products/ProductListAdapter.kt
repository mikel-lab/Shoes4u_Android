package io.guardiaimperial.shoes4u.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.guardiaimperial.shoes4u.databinding.ItemProductBinding
import io.guardiaimperial.shoes4u.domain.model.Product

class ProductListAdapter(
    val onItemClicked: (Int, Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private var list: MutableList<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val imageURL = product.imageUrl.toString()
            Glide.with(binding.productImage.context).load(imageURL).into(binding.productImage)
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price.toString()
            binding.itemProduct.setOnClickListener { onItemClicked.invoke(adapterPosition, product) }
        }
    }
}
