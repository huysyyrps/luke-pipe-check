package com.example.luke_pipe_check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StraightActivity extends AppCompatActivity {
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.etWorkMPa)
    EditText etWorkMPa;
    @BindView(R.id.etPipeOD)
    EditText etPipeOD;
    @BindView(R.id.etUserMpa)
    EditText etUserMpa;
    @BindView(R.id.etPipeCoefficient)
    TextView etPipeCoefficient;
    @BindView(R.id.etCCoefficient)
    EditText etCCoefficient;
    @BindView(R.id.etMinThickness)
    EditText etMinThickness;
    @BindView(R.id.tvCThickness)
    TextView tvCThickness;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(StraightActivity.this, R.color.color_bg_selected);
        setContentView(R.layout.activity_straight_select);
        ButterKnife.bind(this);
    }

    public void getData() {
        String workMpa = etWorkMPa.getText().toString() + "";
        String pipeOD = etPipeOD.getText().toString() + "";
        String userMpa = etUserMpa.getText().toString() + "";
        String pipeCoefficient = etPipeCoefficient.getText().toString() + "";
        String cCoefficient = etCCoefficient.getText().toString() + "";
        String minThickness = etMinThickness.getText().toString() + "";

        if (workMpa.equals("")) {
            Toast.makeText(this, "请输入使用压力", Toast.LENGTH_SHORT).show();
        } else if (pipeOD.equals("")) {
            Toast.makeText(this, "请输入管道外径", Toast.LENGTH_SHORT).show();
        } else if (userMpa.equals("")) {
            Toast.makeText(this, "请输入需用压力", Toast.LENGTH_SHORT).show();
        } else if (pipeCoefficient.equals("")) {
            Toast.makeText(this, "请输入焊接接头系数", Toast.LENGTH_SHORT).show();
        } else if (cCoefficient.equals("")) {
            Toast.makeText(this, "请输入计算系数", Toast.LENGTH_SHORT).show();
        } else if (minThickness.equals("")) {
            Toast.makeText(this, "请输入最小壁厚", Toast.LENGTH_SHORT).show();
        } else {
            double molecule = Double.valueOf(workMpa) * Double.valueOf(pipeOD);
            ;
            double denominator = 2 * (Double.valueOf(userMpa) * Double.valueOf(pipeCoefficient) + Double.valueOf(workMpa) * Double.valueOf(cCoefficient));
            double t = molecule / denominator;
            BigDecimal result = new BigDecimal(t).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvCThickness.setText(result + "");
            if (t < Double.valueOf(minThickness)) {
                tvResult.setText("直管强度校核合格");
                tvResult.setTextColor(getResources().getColor(R.color.green));
            } else {
                tvResult.setText("直管强度校核不合格");
                tvResult.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }

    @OnClick({R.id.tvRight, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tvRight:
                getData();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }
}
