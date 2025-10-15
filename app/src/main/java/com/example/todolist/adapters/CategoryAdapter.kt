package com.example.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Category
import com.example.todolist.databinding.ItemCategoryBinding

class CategoryAdapter(
    var items: List<Category>,
    val onClickListener: (Int) -> Unit,
    val onEditListener: (Int) -> Unit,
    val onDeleteListener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
        holder.binding.editButton.setOnClickListener {
            onEditListener(position)
        }
        holder.binding.deleteButton.setOnClickListener {
            onDeleteListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(category: Category) {
        binding.nameTextView.text = category.name
    }
}