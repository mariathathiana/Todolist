package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.todolist.utils.DatabaseManager

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
        val newRowId = db.insert(table= Category.TABLE_NAME, nullColumnHack= null, values)
        Log.i(tag="DATABASE", msg= "New row inserted in table ${Category.TABLE_NAME} with id: $newRowId")
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
            val updateRows =db.update(table = Category.TABLE_NAME, values, whereClause= "${Category.COLUMN_ID} = ${category.id}", null)
            Log.i(tag="DATABASE", msg="$updateRows rows updated in table ${Category.TABLE_NAME}")
        } catch (e: Exception){
            e.printStackTrace()
        }finally {
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
                table= Category.TABLE_NAME,
                columns= projection,
                selection,
                selectionArgs,
                groupBy=null,
                having=null,
                orderBy=sortOrder
            )

            if (cursor.moveToNext()){
                val id = cursor.getInt(columnIndex= cursor.getColumnIndexOrThrow(Category.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(columnName= Category.COLUMN_NAME))
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

    }

}