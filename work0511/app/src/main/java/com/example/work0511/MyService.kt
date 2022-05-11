package com.example.work0511

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    companion object{
        var flag: Boolean = false //計數器狀態
    }
    //計數器數值
    private var h = 0
    private var m = 0
    private var s = 0
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        flag = intent.getBooleanExtra("flag", false)

        object : Thread(){
            override fun run(){
                while (flag){
                    try{
                        //使用Thread來計算秒數，延遲1秒
                        Thread.sleep(1000)
                    } catch (e: InterruptedException){
                        e.printStackTrace()
                    }
                    //計算器+1
                    s++
                    if(s >= 60){
                        s = 0
                        m++
                        if(m >= 60){
                            m = 0
                            h++
                        }
                    }
                    //產生帶MyMessageg識別字串的Intent
                    val intent = Intent("MyMessage")
                    //把累積的值(經過的秒數)放入Intent
                    val bundle = Bundle()
                    bundle.putInt("H", h)
                    bundle.putInt("M", m)
                    bundle.putInt("S", s)
                    intent.putExtras(bundle)
                    //發送廣播
                    sendBroadcast(intent)
                }
            }
        }.start()

        return Service.START_STICKY
    }
}