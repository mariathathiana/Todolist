package com.example.todolist.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.data.Category
import com.example.todolist.data.Task

class DatabaseManager(context: Context): SQLiteOpenHelper(
    context,
    "toDoList.db",
    null,
    1

)  {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON;")
        db.execSQL(Category.SQL_CREATE_TABLE)
        db.execSQL(Task.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)}


fun onDestroy(db: SQLiteDatabase){
    db.execSQL(Task.SQL_DROP_TABLE)
    db.execSQL(Category.SQL_DROP_TABLE)

}
}