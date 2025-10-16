package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.todolist.utils.DatabaseManager

class CategoryDAO(val context: Context) {

    private var db: SQLiteDatabase? = null

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun close() {
        db?.close()
        db = null
    }

    fun insert(category: Category) {
        val values = ContentValues().apply {
            put(Category.COLUMN_NAME, category.name)
        }

        try {
            open()
            val newRowId = db?.insert(Category.TABLE_NAME, null, values)
            Log.i("DATABASE", "New row inserted in table ${Category.TABLE_NAME} with id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun update(category: Category) {
        val values = ContentValues().apply {
            put(Category.COLUMN_NAME, category.name)
        }

        try {
            open()
            val updateRows = db?.update(
                Category.TABLE_NAME,
                values,
                "${Category.COLUMN_ID} = ?",
                arrayOf(category.id.toString())
            )
            Log.i("DATABASE", "$updateRows rows updated in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun delete(id: Int) {
        try {
            open()
            val deletedRows = db?.delete(
                Category.TABLE_NAME,
                "${Category.COLUMN_ID} = ?",
                arrayOf(id.toString())
            )
            Log.i("DATABASE", "$deletedRows rows deleted in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun find(id: Int): Category? {
        var category: Category? = null
        val projection = arrayOf(Category.COLUMN_ID, Category.COLUMN_NAME)
        val selection = "${Category.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        try {
            open()
            val cursor = db?.query(
                Category.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    val categoryId = it.getInt(it.getColumnIndexOrThrow(Category.COLUMN_ID))
                    val name = it.getString(it.getColumnIndexOrThrow(Category.COLUMN_NAME))
                    category = Category(categoryId, name)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
        return category
    }

    fun findAll(): List<Category> {
        val items = mutableListOf<Category>()
        val projection = arrayOf(Category.COLUMN_ID, Category.COLUMN_NAME)

        try {
            open()
            val cursor = db?.query(
                Category.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            cursor?.use {
                while (it.moveToNext()) {
                    val id = it.getInt(it.getColumnIndexOrThrow(Category.COLUMN_ID))
                    val name = it.getString(it.getColumnIndexOrThrow(Category.COLUMN_NAME))
                    items.add(Category(id, name))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
        return items
    }
}
