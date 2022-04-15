package com.example.work1application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ed_name = findViewById<EditText>(R.id.ed_name)
        val tv_text = findViewById<TextView>(R.id.tv_text)

        val btn_scissor = findViewById<RadioButton>(R.id.btn_scissor)
        val btn_stone = findViewById<RadioButton>(R.id.btn_stone)
        val btn_paper = findViewById<RadioButton>(R.id.btn_paper)

        val btn_mora = findViewById<Button>(R.id.btn_mora)
        val tv_name = findViewById<TextView>(R.id.tv_name)

        val tv_winner = findViewById<TextView>(R.id.tv_winner)
        var tv_player_mora = findViewById<TextView>(R.id.tv_player_mora)
        val tv_computer_mora = findViewById<TextView>(R.id.tv_computer_mora)
        btn_mora.setOnClickListener {
            //判斷字串是否是空白來要求輸入姓名
            if (ed_name.length()<1)
                tv_text.text = "請輸入玩家姓名"
            //tv_winner => tv_winner
            //tv_mmora => tv_player_mora
            //tv_cmora => tv_computer_mora
            else{
                tv_name.text = "名字\n${ed_name.text}"
                tv_player_mora.text = "我方出拳\n${if(btn_scissor.isChecked) "剪刀" else if(btn_stone.isChecked) "石頭" else "布"}"
                //Random() 產生0~1之間不含1的亂數，乘3產生0~2當作電腦的出拳
                val computer = (Math.random()*3).toInt()
                tv_computer_mora.text = "電腦出拳\n${if(computer==0) "剪刀" else if(computer==1) "石頭" else "布"}"
                //用三個判斷是判斷並顯示猜拳結果
                when{
                    btn_scissor.isChecked && computer == 2 ||
                            btn_stone.isChecked && computer == 0 ||
                            btn_paper.isChecked && computer == 1 ->{
                        tv_winner.text = "勝利者\n${ed_name.text}"
                        tv_text.text = "恭喜你獲勝了!!!"
                    }
                    btn_scissor.isChecked && computer == 1 ||
                            btn_stone.isChecked && computer == 2 ||
                            btn_paper.isChecked && computer == 0 ->{
                        tv_winner.text = "勝利者\n電腦"
                        tv_text.text = "可惜，電腦獲勝了!"
                    }
                    else ->{
                        tv_winner.text = "勝利者\n平手"
                        tv_text.text = "平局，請再試一次!"
                    }
                }
            }
        }
    }
}