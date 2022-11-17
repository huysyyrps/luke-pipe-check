package com.example.luke_pipe_check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.adapter.ImageAdapter;
import com.example.luke_pipe_check.bean.DataBean;
import com.example.luke_pipe_check.util.StatusBarUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 直管
 */
public class SelectActivity extends AppCompatActivity {
    List<DataBean> imgList = new ArrayList<>();
    private static boolean isExit = false;
    Intent intent = null;
    //推出程序
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.linPenetration)
    LinearLayout linPenetration;
    @BindView(R.id.linCircularOrStrip)
    LinearLayout linCircularOrStrip;
    @BindView(R.id.linStraightOrElbow)
    LinearLayout linStraightOrElbow;
    @BindView(R.id.linWeldOther)
    LinearLayout linWeldOther;

    //推出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再次返回退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 4000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(SelectActivity.this, R.color.theme_back_color);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        setBannerData();
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorSelectedColor(getResources().getColor(R.color.red));
        banner.isAutoLoop(true);
        banner.setLoopTime(3000);
        banner.setAdapter(new ImageAdapter(imgList, this), true);
    }
    @OnClick({R.id.linPenetration, R.id.linCircularOrStrip, R.id.linStraightOrElbow, R.id.linWeldOther})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linPenetration:
                intent = new Intent(this, LandActivity.class);
                startActivity(intent);
                break;
            case R.id.linCircularOrStrip:
                intent = new Intent(this, CircularOrStripActivity.class);
                startActivity(intent);
                break;
            case R.id.linStraightOrElbow:
                intent = new Intent(this, StraightOrElbowActivity.class);
                startActivity(intent);
                break;
            case R.id.linWeldOther:
                intent = new Intent(this, WeldOtherActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void setBannerData() {
        DataBean dataBean1 = new DataBean();
        dataBean1.setTitle("");
        dataBean1.setImageRes(R.drawable.banner_select1);
        imgList.add(dataBean1);

        DataBean dataBean2 = new DataBean();
        dataBean2.setTitle("");
        dataBean2.setImageRes(R.drawable.banner_select2);
        imgList.add(dataBean2);

        DataBean dataBean3 = new DataBean();
        dataBean3.setTitle("");
        dataBean3.setImageRes(R.drawable.banner_select3);
        imgList.add(dataBean3);

        DataBean dataBean4 = new DataBean();
        dataBean4.setTitle("");
        dataBean4.setImageRes(R.drawable.banner_select4);
        imgList.add(dataBean4);

    }

}