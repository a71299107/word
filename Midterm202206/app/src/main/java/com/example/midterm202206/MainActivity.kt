package com.example.midterm202206

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var dbrw: SQLiteDatabase
    private var items: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //取得資料庫實體
        dbrw = MyDBHelper(this).writableDatabase
        val righttext = "OK!"
        val wrongtext = "Wrong username or password!"
        var toastmsg: String = "wrong"
        var username1: EditText = findViewById(R.id.Name)
        var password1: EditText = findViewById(R.id.Password)
        val btnlogin: Button = findViewById<Button>(R.id.button)
        val btnRegister: Button = findViewById<Button>(R.id.button2)
        val btnclear: Button = findViewById<Button>(R.id.button4)
        var tvstatus: TextView = findViewById(R.id.tv_status)

        //清除資料庫內容
        btnclear.setOnClickListener {
            dbrw.execSQL("DELETE FROM myTable")
            Toast.makeText(this@MainActivity, "清除成功", Toast.LENGTH_LONG).show()
        }
        //註冊按鈕
        btnRegister.setOnClickListener {
            username1.text.clear()
            password1.text.clear()
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
        //登入按鈕
        btnlogin.setOnClickListener {
            //顯示錯誤訊息
            tvstatus.text = ""
            //判斷帳號或密碼有輸入
            if (username1.length() > 0 || password1.length() > 0) {
                //從資料庫抓取資料
                val queryString = if (username1.length() < 1)
                    "SELECT * FROM user"
                else  //從yTable(表單) 在 username(欄位)中查詢資料 '找到這筆資料後轉換成文字'
                    "SELECT * FROM myTable WHERE username LIKE '${username1.text}'"
                //rawQuery裡面的參數是數據庫查詢語句
                val c = dbrw.rawQuery(queryString, null)
                c.moveToFirst() //從第一筆開始輸出
                items.clear() //清空舊資料

                //如果資料庫沒抓到資料
                if (c.count == 0) {
                    tvstatus.text = "資料庫目前沒有資料"
                } else {  //如果抓到資料
                    //資料庫c.getString(0)是抓取ID
                    //取得資料庫內的帳號
                    val dbusername = c.getString(0).toString()
                    //取得資料庫內的密碼
                    val dbPassword = c.getString(1).toString()
                    //如果帳號和密碼正確
                    if ((username1.text.toString() == dbusername) && (password1.text.toString() == dbPassword)) {
                        //將欄位清空
                        username1.text.clear()
                        password1.text.clear()
                        //跳轉畫面
                        val intent = Intent(this, MainActivity2::class.java)
                        val bundle = Bundle()
                        bundle.putString("key1", "${dbusername}")
                        intent.putExtras(bundle)
                        startActivity(intent)
                        Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                    } else {  //如果資料帳號或密碼錯誤
                        tvstatus.text = "wrong username or password"
                        Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show()
                        Log.e("MainActivity", "wrong username or password")
                    }
                }
            } else {
                Toast.makeText(this, "帳號或密碼沒輸入", Toast.LENGTH_LONG).show()
            }
        }
    }
}