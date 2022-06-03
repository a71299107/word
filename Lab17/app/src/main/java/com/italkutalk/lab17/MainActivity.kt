package com.italkutalk.lab17

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    private lateinit var btn_query: Button  //宣告按鈕
    private val receive : BroadcastReceiver = object : BroadcastReceiver(){  //BroadcastReceiver是廣播接收器
        override fun onReceive(context: Context?, intent: Intent?) {
            //intent是對要執行的操作的抽象描述
            //extras是Bundle任何附加信息。這可用於向組件提供擴展信息。例如，如果我們有一個發送電子郵件消息的操作，我們還可以在此處包含額外的數據以提供主題、正文等
            //，抓取Key
            intent?.extras?.let {
                val jsonData = it.getStringArray("json")  //本身獲取字符串陣列列表(可以隨意設置，在此設為key為json)
                if (jsonData != null){  //如果抓到的資料不為空的話
                    val items = arrayOfNulls<String>(jsonData.size)  //空值數組(資料的大小)代入items，給予相同大小的空間，以便塞入大小相同的資料
                    for (i in jsonData.indices){
                        val data = Gson().fromJson(jsonData[i], Data::class.java)
                        items[i] = String.format(  //字串加入想要的內容後，讓items[i]空間指向它
                            "id:%d\nname:%s",
                            data.id,
                            data.name
                        )
                    }
                    this@MainActivity.runOnUiThread()  //顯示內容
                    {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("資料")  //設置顯示的標題
                            .setItems(items, null)
                            .show()  //顯示AlertDialog
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_query = findViewById(R.id.btn_query)  //綁定按鈕
        val filter = IntentFilter("Message")  //過濾器

        registerReceiver(receive,filter)  //註冊廣播接收器
        btn_query.setOnClickListener {  //按鈕觸發事件
            val responseHandler = object : JsonHttpResponseHandler(){  //抓取Json型態的HTTP的回覆處理程序的方法後代入變數
                override fun onSuccess(  //如果連線成功
                    statusCode: Int,  //狀態碼的型態，類似編號
                    handers: Array<out Header>?,  //標題:陣列型態
                    response: JSONArray  //回覆，JSON陣列
                ){
                    val intent = Intent("Message")  //訊息
                    val items = arrayOfNulls<String>(response.length())  //空值陣列
                    for (i in 0 until response.length()){ //查看幾個訊息就顯示幾個
                        items[i] = response.getJSONObject(i).toString()  //將抓到的資料每一筆轉換成字串後丟進空值數組內
                    }
                    intent.putExtra("json",items)  //把資料匯入intent中
                    sendBroadcast(intent)  //發送廣播
                }
                //發送失敗執行此方法
                override fun onFailure(
                    statusCode: Int,  //狀態碼的型態，類似編號
                    headers: Array<out Header>?,  //標題:陣列型態
                    throwable: Throwable?,  //拋出異常，也就是專門用來處理異常的類
                    errorResponse: JSONArray  //錯誤回覆:JSON陣列
                ){
                    Log.i("Fail","發送失敗")
                }
            }
            val reqly = AsyncHttpClient()  //AsyncHttpClient 可用於在您的 Android 應用程序中發出異步 GET、POST、PUT 和 DELETE HTTP 請求
            reqly.get("https://jsonplaceholder.typicode.com/comments?postId=1",responseHandler)  //連線的方法.獲取(網址,回覆的方法)
        }
    }
}