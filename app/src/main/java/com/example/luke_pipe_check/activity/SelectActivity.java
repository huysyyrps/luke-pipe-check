package com.example.luke_pipe_check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 直管
 */
public class SelectActivity extends AppCompatActivity {
    @BindView(R.id.tvPenetration)
    TextView tvPenetration;
    @BindView(R.id.tvStraight)
    TextView tvStraight;
    @BindView(R.id.tvElbow)
    TextView tvElbow;
    @BindView(R.id.tvCircular)
    TextView tvCircular;
    @BindView(R.id.tvStrip)
    TextView tvStrip;

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
        new StatusBarUtils().setWindowStatusBarColor(SelectActivity.this, R.color.theme_color);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvPenetration, R.id.tvStraight, R.id.tvElbow, R.id.tvCircular, R.id.tvStrip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPenetration:
                intent = new Intent(this, LandActivity.class);
                startActivity(intent);
                break;
            case R.id.tvStraight:
                intent = new Intent(this, StraightActivity.class);
                startActivity(intent);
                break;
            case R.id.tvElbow:
                intent = new Intent(this, ElbowAvtivity.class);
                startActivity(intent);
                break;
            case R.id.tvCircular:
                intent = new Intent(this, CircularActivity.class);
                startActivity(intent);
                break;
            case R.id.tvStrip:
                intent = new Intent(this, StripActivity.class);
                startActivity(intent);
                break;
        }
    }
}