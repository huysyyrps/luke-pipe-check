package com.example.luke_pipe_check.constant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.activity.SelectActivity;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mHandler = new Handler();
        new StatusBarUtils().setWindowStatusBarColor(WelcomeActivity.this, R.color.white);
        mHandler.postDelayed(new Runnable() {
            public void run() {
//                String s = load();
//
//                long LNowDate = new Date().getTime();;
//                Log.e("X",LNowDate+"");
//                if (s==null||s.equals("")){
//                    Intent intent=new Intent(WelcomeActivity.this, ActivityCode.class);
//                    startActivity(intent);
//                    finish();
//                }else if (LNowDate<Long.valueOf(s)){
//                    Intent intent=new Intent(WelcomeActivity.this, SelectActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else if (LNowDate==Long.valueOf(s)){
//                    Toast.makeText(WelcomeActivity.this, "激活码即将到期", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(WelcomeActivity.this, SelectActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else if (LNowDate>Long.valueOf(s)){
//                    Intent intent=new Intent(WelcomeActivity.this, ActivityCode.class);
//                    startActivity(intent);
//                    finish();
//                }
                Intent intent=new Intent(WelcomeActivity.this, SelectActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000); //延迟2秒跳转

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