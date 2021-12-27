package com.example.luke_pipe_check;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.tvProgress)
    CircleTextProgressbar tvProgress;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Toast.makeText(this, "11111", Toast.LENGTH_SHORT).show();
        mHandler = new Handler();
        new StatusBarUtils().setWindowStatusBarColor(WelcomeActivity.this, R.color.white);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                String s = load();

                long LNowDate = new Date().getTime();;
                Log.e("X",LNowDate+"");
                if (s==null||s.equals("")){
                    Intent intent=new Intent(WelcomeActivity.this, ActivityCode.class);
                    startActivity(intent);
                    finish();
                }else if (LNowDate<Long.valueOf(s)){
                    Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (LNowDate==Long.valueOf(s)){
                    Toast.makeText(WelcomeActivity.this, "激活码即将到期", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (LNowDate>Long.valueOf(s)){
                    Intent intent=new Intent(WelcomeActivity.this, ActivityCode.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000); //延迟2秒跳转

        // 模拟网易新闻跳过。
        tvProgress = (CircleTextProgressbar) findViewById(R.id.tvProgress);
        tvProgress.setOutLineColor(Color.TRANSPARENT);
        tvProgress.setInCircleColor(Color.parseColor("#AAC6C6C6"));
        tvProgress.setProgressColor(Color.BLUE);
        tvProgress.setProgressLineWidth(3);
        tvProgress.reStart();
    }

    //从“data/data/com.example.项目名/files/data”中读取String
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");//文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}