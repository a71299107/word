package com.example.word0413

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var my_progress_data = 0
    //抓取物件過來
    private lateinit var ed_height: EditText  //var是引用局部的可變變量的變數
    private lateinit var ed_weight: EditText
    private lateinit var btn_boy: RadioButton
    private lateinit var btn_girl: RadioButton
    private lateinit var btn_calculate: Button
    //體重和脂肪
    private lateinit var tv_weight: TextView
    private lateinit var tv_bmi: TextView
    //進度
    private lateinit var ll_progress: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var tv_progress: TextView

    //物件被按下所執行的方法
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與 XML 元件綁定
        //抓取物件過來
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        //性別按鈕
        btn_boy = findViewById(R.id.btn_boy)
        btn_girl = findViewById(R.id.btn_girl)
        //計算按鈕
        btn_calculate = findViewById(R.id.btn_calculate)
        //體重和脂肪
        tv_weight = findViewById(R.id.tv_weight)
        tv_bmi = findViewById(R.id.tv_bmi)
        //進度
        ll_progress = findViewById(R.id.ll_progress)
        progressBar = findViewById(R.id.progressBar)
        progressBar2 = findViewById(R.id.progressBar2)
        tv_progress = findViewById(R.id.tv_progress)

        //開始按鈕監聽事件
        btn_calculate.setOnClickListener {
            var msg = Message()
            when {
                ed_height.length() < 1 -> msg.what = 1 //如果沒有輸入身高
                ed_weight.length() < 1 -> msg.what = 2 //如果沒有輸入體重
                else -> {
                    ll_progress.visibility = View.VISIBLE  //顯示進度條
                    runThread()
                }
            }
            handler.sendMessage(msg) //判定完後發送mag到handler
        }
    }

    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    //Handler物件等待接收訊息
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1){  //如果沒有輸入身高
            showToast("請輸出身高")
        }
        if (msg.what == 2){  //如果沒有輸入體重
            showToast("請輸出體重")
        }
        if (msg.what == 3){  //如果沒有輸入
            ll_progress.visibility = View.GONE //隱藏隱藏條
            val cal_height = ed_height.text.toString().toDouble() //身高，val是引用不可更改的變數，也就是只能代入一次變數
            val cal_weight = ed_weight.text.toString().toDouble() //體重
            val cal_standweight: Double  //男性
            val cal_bodyfat: Double  //女性
            if (btn_boy.isChecked) {  //如果男性被點選
                cal_standweight = (cal_height - 80) * 0.7 //體重
                cal_bodyfat = (cal_weight - 0.88 * cal_standweight) / cal_weight * 100 //體脂肪
            } else {  //否則
                cal_standweight = (cal_height - 70) * 0.6 //體重
                cal_bodyfat = (cal_weight - 0.82 * cal_standweight) / cal_weight * 100 //體脂肪
            }
            tv_weight.text = "標準體重 \n${String.format("%.2f", cal_standweight)}" //顯示體重
            tv_bmi.text = "體脂肪 \n${String.format("%.2f", cal_bodyfat)}" //顯示BMI
        }
        if(msg.what == 4){
            //進度更新
            progressBar2.progress = my_progress_data
            tv_progress.text = "$my_progress_data%"  //$的後方要放變數
        }
        true
    }

    //多執行緒
    private fun runThread(){
        Thread{
            //初始化進度條
            progressBar2.progress = 0
            tv_progress.text = "0%"

            while(my_progress_data < 100){
                try{
                    Thread.sleep(50)
                    val msg = Message()  //重新宣告msg
                    //累加
                    my_progress_data++
                    //將msg改變值，msg代入handler
                    msg.what = 4
                    handler.sendMessage(msg)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
            val msg = Message()  //重新宣告msg
            msg.what = 3
            handler.sendMessage(msg)
        }.start()
    }
}