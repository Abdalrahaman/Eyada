package com.omranic.eyada.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omranic.eyada.adapter.home.CategoryAdapter.CategoryViewHolder
import com.omranic.eyada.databinding.CategoryItemBinding
import com.omranic.eyada.model.Category

class CategoryAdapter(private var categories: List<Category>) : RecyclerView.Adapter<CategoryViewHolder>() {

    private lateinit var _categoryItemBinding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        _categoryItemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(_categoryItemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategoryData(newCategories: List<Category>){
        categories = newCategories
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int){
            binding.tvCategoryName.text = categories[index].name
            binding.ivCategoryImage.setImageResource(categories[index].image)
        }
    }

}