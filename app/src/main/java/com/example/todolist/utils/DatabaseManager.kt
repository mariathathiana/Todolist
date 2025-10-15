package com.example.todolist.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.data.Category

class DatabaseManager(context: Context): SQLiteOpenHelper(
    context,
    name ="toDoList.db",
    factory = null,
    version =1

)  {

    override fun onCreate(db: SQLiteDatabase?) {
        db.execSQL(Category.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            onDestroy(db)
            onCreate(db)}
    }

    fun onDestroy(db: SQLiteDatabase){
        db.execSQL(Category.SQL_DROP_TABLE)

    }
}