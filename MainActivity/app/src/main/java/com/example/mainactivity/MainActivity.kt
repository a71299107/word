package com.example.mainactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_choice = findViewById<Button>(R.id.btn_choice)
        btn_choice.setOnClickListener{
            startActivityForResult(Intent(this,MainActivity2::class.java),1)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val tv_meal = findViewById<TextView>(R.id.tv_meal)
        data?.extras?.let{
            if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
                tv_meal.text = "飲料: ${it.getString("drink")}\n\n" +
                        "甜度: ${it.getString("sugar")} \n\n" +
                        "冰塊: ${it.getString("ice")}"
            }
        }
    }
}