package com.example.mainactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val btn_send = findViewById<Button>(R.id.btn_send)
        val ed_drink = findViewById<RadioGroup>(R.id.ed_drink)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val radioGroup2 = findViewById<RadioGroup>(R.id.radioGroup2)
        btn_send.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("drink",ed_drink.findViewById<RadioButton>
                (ed_drink.checkedRadioButtonId).text.toString())
            bundle.putString("sugar", radioGroup.findViewById<RadioButton>
                (radioGroup.checkedRadioButtonId).text.toString())
            bundle.putString("ice", radioGroup2.findViewById<RadioButton>
                (radioGroup2.checkedRadioButtonId).text.toString())

            val intent = Intent().putExtras(bundle)
            //使用serResult()方法，儲存要返回的資料
            setResult(Activity.RESULT_OK,Intent().putExtras(bundle))
            finish()
        }
    }
}