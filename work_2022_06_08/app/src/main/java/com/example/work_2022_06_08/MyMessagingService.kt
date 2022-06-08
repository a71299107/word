package com.example.work_2022_06_08

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService() {
    //APP取得新token時呼叫，通常是在第一次啟動APP時會自動與Firebase註冊
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("Firebase","onNewToken $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("Firebase","onMessageReceived")
        //判斷收到的msg不為null
        message.let{
            Log.e("Firebase",it.from!!)
            //透過for loop將msg夾帶的資料輸出
            for(entry in it.data.entries)
                Log.e("message","${entry.key}/${entry.value}")
        }
    }
}