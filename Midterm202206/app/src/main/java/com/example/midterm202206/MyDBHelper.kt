package com.example.midterm202206

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//自定建構子並繼承SQLiteOpenHelper
class MyDBHelper(
    context: Context,
    name: String = database,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = v,
)
    : SQLiteOpenHelper(context, name, factory, version) {  //繼承SQLiteOpenHelper類別
    companion object {
        //資料庫名稱
        private const val database = "myDatabase.db"
        //資料庫版本
        private const val v = 1
    }
    //這裡寫要加入建立資料表的SQL語法
    override fun onCreate(db: SQLiteDatabase) {
        //這裡寫要加入建立資料表的SQL語法
        //建立 myTable 資料表，表內有 username 字串(text)和 password 整數(integer)兩個值
        db.execSQL("CREATE TABLE myTable(username text PRIMARY KEY, password int NOT NULL)")
    }
    //這裡寫要升級資料庫版本的SQL語法
    override fun onUpgrade(
        db: SQLiteDatabase, oldVersion: Int,
        newVersion: Int,
    ) {
        //升級資料庫版本時，刪除舊資料表，並重新執行 onCreate()，建立新資料表
        db.execSQL("DROP TABLE IF EXISTS myTable")
        //刪除資料表後重新呼叫onCreate()方法建立新的資料表
        onCreate(db)
    }
/*
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.version = 1
    }
 */
}