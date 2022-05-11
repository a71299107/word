package com.example.work0504

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //宣告變數和給予型態
    lateinit var btn_start: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //綁定元件
        btn_start = findViewById(R.id.btn_start)

        btn_start.setOnClickListener {
            //使用startService，從目前Activity(this)啟動MyService元件
            startService(Intent(this, MyService::class.java))
            Toast.makeText(this, "啟動Service", Toast.LENGTH_SHORT)
            //關閉Activity
            finish()
        }
    }
}