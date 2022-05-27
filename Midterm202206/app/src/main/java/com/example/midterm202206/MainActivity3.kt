package com.example.midterm202206

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf

class MainActivity3 : AppCompatActivity() {
    private lateinit var dbrw: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var username3: EditText = findViewById(R.id.username3)
        var password3: EditText = findViewById(R.id.password3)
        var btnaccess: Button = findViewById(R.id.button3)
        //取得資料庫實體，使用writableDatabase屬性建立可寫入的資料庫
        dbrw = MyDBHelper(this).writableDatabase

        btnaccess.setOnClickListener {
            try{
                dbrw.execSQL(
                    "INSERT INTO myTable(username,password) VALUES(?,?)",
                    arrayOf(username3.text.toString(),
                        password3.text.toString())
                )
                showToast("新增成功")
                cleanEditText()
            }catch (e: Exception) {
                showToast("新增失敗:$e")
            }
            finish()  //結束目前的畫面，跳回上一頁
        }
    }
    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
    //清空輸入的書名與價格
    private fun cleanEditText() {
        findViewById<EditText>(R.id.username3).setText("")
        findViewById<EditText>(R.id.password3).setText("")
    }
}