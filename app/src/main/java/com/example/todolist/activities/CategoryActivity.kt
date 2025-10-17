package com.example.todolist.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.R
import com.example.todolist.data.Category
import com.example.todolist.data.CategoryDAO
import com.example.todolist.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("CATEGORY_ID", -1)

        categoryDAO = CategoryDAO(this)

        if (id != -1) {
            // Edit
            category = categoryDAO.find(id)!!
            supportActionBar?.title = "Editar categoría"
        } else {
            // Crear
            category = Category(-1, "")
            supportActionBar?.title = "Crear categoría"
        }

        binding.nameEditText.editText?.setText(category.name)

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.editText?.text.toString()

            category.name = name

            if (category.id == -1) {
                categoryDAO.insert(category)
            } else {
                categoryDAO.update(category)
            }

            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}