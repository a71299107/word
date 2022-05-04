package com.example.hw2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Main2Activity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    lateinit var btn_timePicker: Button
    lateinit var tv_textTime: TextView
    lateinit var btn_logout: Button
    lateinit var tv_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_main)

        btn_timePicker = findViewById(R.id.btn_timePicker)
        tv_textTime = findViewById(R.id.tv_textTime)
        btn_logout = findViewById(R.id.btn_logout)
        tv_text = findViewById(R.id.tv_text)
        pickDate()

        intent?.extras?.let {
            tv_text.text = "名字為：${it.getString("username")}"
        }

        btn_logout.setOnClickListener {
            startActivityForResult(Intent(this, MainActivity::class.java), 1)
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)  //天
        month = cal.get(Calendar.MONTH)  //月
        year = cal.get(Calendar.YEAR)  //年
        hour = cal.get(Calendar.HOUR)  //小時
        minute = cal.get(Calendar.MINUTE)  //分鐘
    }

    private fun pickDate() {
        btn_timePicker.setOnClickListener {
            getDateTimeCalendar()
            //context：建立日期選擇對話框的 parent context
            //listener：監聽使用者選擇的日期，使用者選擇日期後會調用監聽器
            //year：指定日期選擇對話框初始要顯示的年份
            //month：指定日期選擇對話框初始要顯示的月份
            //dayOfMonth：指定日期選擇對話框初始要顯示的日期
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar()
        //第一個參數是 Context , 也就是說必須把 MainActivity 本身或者 Context 物件傳入
        //第二個參數是 OnTimeSetListener , 這邊是實作 btn_timePicker 這個介面的事件, 它提供使用者操控完時間介面後, 所傳回的時間
        //第三個是現在是幾點, 我們可以透過 Canlendar 的幫忙得到這個資訊
        //第四個是現在是幾分, 我們可以透過 Canlendar 的幫忙得到這個資訊。
        //最後一個參數是boolean, true代表呈現24小時, false代表只顯示12小時
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        tv_textTime.text = "$savedDay-$savedMonth-$savedYear\n Hour $savedHour Minute: $savedMinute"
    }
}