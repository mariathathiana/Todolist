package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.todolist.utils.DatabaseManager
import com.example.todolist.data.Category

class CategoryDAO (val context: Context){

    private lateinit var db: SQLiteDatabase

    private fun open(){
        db = DatabaseManager(context).writableDatabase
    }

    private fun close(){
        db.close()
    }

    fun insert(category: Category){

        val values = ContentValues()
        values.put(Category.COLUMN_NAME, category.name)

    try {
        open()
        val newRowId = db.insert(Category.TABLE_NAME,  null, values)
        Log.i("DATABASE", "New row inserted in table ${Category.TABLE_NAME} with id: $newRowId")
    }catch (e:Exception){
        e.printStackTrace()
    }finally{
        close()
    }

    }

    fun update(category: Category) {
        val values = ContentValues()
        values.put(Category.COLUMN_NAME, category.name)

        try {
            open()
            val updateRows =db.update(Category.TABLE_NAME, values, "${Category.COLUMN_ID} = ${category.id}", null)
            Log.i("DATABASE", "$updateRows rows updated in table ${Category.TABLE_NAME}")
        } catch (e: Exception){
            e.printStackTrace()
        }finally {
            close()
        }
    }

    fun delete(id: Int) {
        try {
            open()

            // Insert the new row, returning the primary key value of the new row
            val deletedRows = db.delete(Category.TABLE_NAME, "${Category.COLUMN_ID} = $id", null)
            Log.i("DATABASE", "$deletedRows rows deleted in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun find(id: Int): Category?{
        var category: Category?= null
        val projection = arrayOf(Category. COLUMN_ID, Category.COLUMN_NAME)
        val selection = "${Category.COLUMN_ID} = $id"
        val selectionArgs = null

        val sortOrder = null

        try {
            open()
            val cursor = db.query(
                Category.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )

            if (cursor.moveToNext()){
                val id = cursor.getInt( cursor.getColumnIndexOrThrow(Category.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow( Category.COLUMN_NAME))
                category = Category(id, name)
            }

        } catch (e: Exception){
            e.printStackTrace()
        }finally {
            close()
        }
        return category
    }

    fun findAll(): List<Category>{
        val items: MutableList<Category> = mutableListOf()

        val projection= arrayOf(Category.COLUMN_ID, Category.COLUMN_NAME )

        val  selection = null

        val selectionArgs = null

        val  sorterOrder = null

        try {
            open()

            val cursor = db.query(
                Category.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sorterOrder
            )

            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Category.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME))
                val category = Category(id, name)
                items.add(category)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            close()
        }
        return items

    }

}