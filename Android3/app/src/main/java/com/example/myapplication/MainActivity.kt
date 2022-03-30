@file:Suppress("DEPRECATION")

package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //宣告變數
        var button1 = findViewById<Button>(R.id.button1)
        var button2 = findViewById<Button>(R.id.button2)
        var button3 = findViewById<Button>(R.id.button3)
        var button4 = findViewById<Button>(R.id.button4)
        var button5 = findViewById<Button>(R.id.button5)
        //第一個按鈕聆聽事件
        button1.setOnClickListener{
            Toast.makeText(this,"文字消息",Toast.LENGTH_SHORT).show()
        }
        //第二個按鈕事件
        button2.setOnClickListener{
            //Step1 初始化Toast
            val toast = Toast(this)
            //將設計的xml指定為view 來放入Toast中呈現
            var view = layoutInflater.inflate(R.layout.toast_custom, null)

            //Step2 在畫面中顯示位置
            toast.setGravity(Gravity.TOP, 0, 50)
            //Step3 在畫面中顯示的持續時間
            toast.duration = Toast.LENGTH_SHORT
            //Step4 放入自定義的畫面
            toast.view = layoutInflater.inflate(R.layout.toast_custom, null)
            
            //Step5 顯示畫面
            toast.show()
        }
        //第三個按鈕事件
        button3.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("按鍵式對話框")
                .setMessage("對話框內容")
                .setNeutralButton("取消"){ dialog, which ->
                    Toast.makeText(this,"取消", Toast.LENGTH_SHORT).show() //顯示取消按鈕被點選
                }
                .setNegativeButton("拒絕"){ dialog, which ->
                    Toast.makeText(this,"拒絕", Toast.LENGTH_SHORT).show() //顯示拒絕按鈕被點選
                }
                .setPositiveButton("確定"){ dialog, which ->
                    Toast.makeText(this,"確定",Toast.LENGTH_SHORT).show() //顯示確定按鈕被點選
                }.show()
        }
        button4.setOnClickListener{
            val list_item = arrayOf("對話框選項1", "對話框選項2", "對話框選項3", "對話框選項4", "對話框選項5") //建立要顯示在地列表上的字串
            AlertDialog.Builder(this)
                .setTitle("列表式對話框")
                .setItems(list_item){ dialogInterface, i ->
                    //顯示被點選的項目
                    Toast.makeText(this, "你選的是" + list_item[i], Toast.LENGTH_SHORT).show()
                }.show()
        }
        button5.setOnClickListener{
            val list_item = arrayOf("對話框選項1", "對話框選項2", "對話框選項3", "對話框選項4", "對話框選項5")
            var position = 0
            //建立AlertDialog物件
            AlertDialog.Builder(this)
                .setTitle("單選式對話框")
                .setSingleChoiceItems(list_item, 0){ dialogInterface, i ->
                    position = i //紀錄被按下的位置
                }
                .setPositiveButton("確定"){ dialog, which ->
                    Toast.makeText(this,"你選的是" + list_item[position],
                        Toast.LENGTH_SHORT).show() //顯示被點選的項目
                }.show()
        }
    }
}