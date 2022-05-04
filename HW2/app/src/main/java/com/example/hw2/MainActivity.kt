package com.example.hw2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var button : Button
    var correct_username = "aaa"
    var correct_password = "123"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與XML元件綁定
        username = findViewById(R.id.username)
        password = findViewById(R.id.Password)
        button = findViewById(R.id.button)
        //按鈕觸發事件
        button.setOnClickListener {
            rundata()
        }
    }

    fun rundata() {
        if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())
        ) {
            Toast.makeText(this, "Empty data provided", Toast.LENGTH_LONG).show();
        } else if (username.getText().toString().equals(correct_username)) {
            if (password.getText().toString().equals(correct_password)) {
                val b = Bundle()
                b.putString("username","${username.text}")  //選擇要存入的資料   username.text.toString()
                val i = Intent(this, Main2Activity::class.java)  //宣告意圖
                i.putExtras(b)  //放入資料
                setResult(Activity.RESULT_OK,Intent().putExtras(b))//儲存要返回的資料
                startActivityForResult(i,1)  //開始動作
                Toast.makeText(this, "Success Login", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
        }
    }

}