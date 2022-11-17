package com.example.luke_pipe_check;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.activity.SelectActivity;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCode extends AppCompatActivity {

    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tvCopy)
    TextView tvCopy;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvSend)
    TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        new StatusBarUtils().setWindowStatusBarColor(ActivityCode.this, R.color.black);
        String deviceId = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String time = String.valueOf(new Date().getTime());
        String strRand = "";
        for (int i = 0; i < 20; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        //android ID+当前时间+10位随机数
        String code1 = deviceId + time + strRand;
        tvCode.setText(code1);
    }

    @OnClick({R.id.tvCopy, R.id.tvSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCopy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", tvCode.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                break;
            case R.id.tvSend:
                if (etCode.getText().toString().equals("lukejiance-hxf-development")){
                    Intent intent = new Intent(this, SelectActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //md5编码
                    String strmd5 = md5(tvCode.getText().toString());
                    StringBuffer buf = new StringBuffer(strmd5);
                    String code = buf.reverse().toString();

                    String writeData = etCode.getText().toString();
                    String decryptCode = null, userDate = null;
                    try {
                         decryptCode = writeData.split(";;")[0];
                         userDate = writeData.split(";;")[1];
                    }catch (Exception e){
                        Toast.makeText(this, R.string.leavecode_error, Toast.LENGTH_SHORT).show();
                        break;
                    }

                    if (decryptCode.equals(code)) {
                        StringBuffer writeDateBuf = new StringBuffer(userDate);
                        String writeDate = writeDateBuf.reverse().toString();
                        //如果sp为空或者文件夹不存在文件就保存
                        save(writeDate);
                        Intent intent = new Intent(this, SelectActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this, R.string.leavecode_error, Toast.LENGTH_SHORT).show();
                    }
                    Log.e("XXX", strmd5);
                    Log.e("XXXX", code);
                    Log.e("XXXXX", decryptCode);
                    Log.e("XXXXXX", load());
                }
                break;
        }
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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