package com.example.luke_pipe_check.constant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.ActivityCode;
import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.activity.SelectActivity;
import com.example.luke_pipe_check.adapter.ImageAdapter;
import com.example.luke_pipe_check.bean.DataBean;
import com.example.luke_pipe_check.util.StatusBarUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btn)
    Button btn;
    List<DataBean> imgList = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(WelcomeActivity.this, R.color.black);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        setBannerData();

        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorSelectedColor(getResources().getColor(R.color.red));
        banner.isAutoLoop(true);
        banner.setLoopTime(2000);
        banner.setAdapter(new ImageAdapter(imgList, this), true);

        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    textView.setVisibility(View.GONE);
                    btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    private void setBannerData() {
        DataBean dataBean1 = new DataBean();
        dataBean1.setTitle("");
        dataBean1.setImageRes(R.drawable.banner1);
        imgList.add(dataBean1);

        DataBean dataBean2 = new DataBean();
        dataBean2.setTitle("");
        dataBean2.setImageRes(R.drawable.banner2);
        imgList.add(dataBean2);

        DataBean dataBean3 = new DataBean();
        dataBean3.setTitle("");
        dataBean3.setImageRes(R.drawable.banner3);
        imgList.add(dataBean3);

        DataBean dataBean4 = new DataBean();
        dataBean4.setTitle("");
        dataBean4.setImageRes(R.drawable.banner4);
        imgList.add(dataBean4);

    }

    @OnClick({R.id.textView, R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:0537-6588260"));
                startActivity(intentCall);
                break;
            case R.id.btn:
                String s = load();
                long LNowDate = new Date().getTime();
                Log.e("X", LNowDate + "");
                Intent intent;
                if (s == null || s.equals("")) {
                    intent = new Intent(WelcomeActivity.this, ActivityCode.class);
                    startActivity(intent);
                    finish();
                } else if (LNowDate < Long.valueOf(s)) {
                    intent = new Intent(WelcomeActivity.this, SelectActivity.class);
                    startActivity(intent);
                    finish();
                } else if (LNowDate == Long.valueOf(s)) {
                    Toast.makeText(WelcomeActivity.this, "激活码即将到期", Toast.LENGTH_SHORT).show();
                    intent = new Intent(WelcomeActivity.this, SelectActivity.class);
                    startActivity(intent);
                    finish();
                } else if (LNowDate > Long.valueOf(s)) {
                    intent = new Intent(WelcomeActivity.this, ActivityCode.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}