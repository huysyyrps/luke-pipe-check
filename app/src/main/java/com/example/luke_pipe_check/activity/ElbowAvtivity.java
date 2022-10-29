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

public class ElbowAvtivity extends AppCompatActivity {

    /**
     * 弯头
     */
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
    @BindView(R.id.etRadius)
    EditText etRadius;
    @BindView(R.id.etWithinMin)
    EditText etWithinMin;
    @BindView(R.id.etOutMin)
    EditText etOutMin;
    @BindView(R.id.tvWithinI)
    TextView tvWithinI;
    @BindView(R.id.tvOutI)
    TextView tvOutI;
    @BindView(R.id.tvWithinTW)
    TextView tvWithinTW;
    @BindView(R.id.tvOutTW)
    TextView tvOutTW;
    @BindView(R.id.tvWithinResult)
    TextView tvWithinResult;
    @BindView(R.id.tvOutResult)
    TextView tvOutResult;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(ElbowAvtivity.this, R.color.color_bg_selected);
        setContentView(R.layout.activity_select_elbow_avtivity);
        ButterKnife.bind(this);
    }

    public void getData() {
        String workMpa = etWorkMPa.getText().toString() + "";
        String pipeOD = etPipeOD.getText().toString() + "";
        String userMpa = etUserMpa.getText().toString() + "";
        String pipeCoefficient = etPipeCoefficient.getText().toString() + "";
        String cCoefficient = etCCoefficient.getText().toString() + "";
        String radius = etRadius.getText().toString() + "";
        String withinMin = etWithinMin.getText().toString() + "";
        String outMin = etOutMin.getText().toString() + "";


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
        } else if (radius.equals("")) {
            Toast.makeText(this, "请输入弯头的弯曲半径", Toast.LENGTH_SHORT).show();
        } else if (withinMin.equals("")) {
            Toast.makeText(this, "请输入实测弯头内侧壁厚最小值", Toast.LENGTH_SHORT).show();
        } else if (outMin.equals("")) {
            Toast.makeText(this, "请输入实测弯头外侧壁厚最小值", Toast.LENGTH_SHORT).show();
        } else {
            double moleculeWithinI = 4 * (Double.valueOf(radius) / Double.valueOf(pipeOD)) - 1;
            double denominatorWithinI = 4 * (Double.valueOf(radius) / Double.valueOf(pipeOD)) - 2;
            double withinI = moleculeWithinI / denominatorWithinI;
            BigDecimal BDWithinI = new BigDecimal(withinI).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvWithinI.setText(BDWithinI + "");

            double moleculeOutI = 4 * (Double.valueOf(radius) / Double.valueOf(pipeOD)) - 1;
            double denominatorOutI = 4 * (Double.valueOf(radius) / Double.valueOf(pipeOD)) + 2;
            double outI = moleculeOutI / denominatorOutI;
            BigDecimal BDOutI = new BigDecimal(outI).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvOutI.setText(BDOutI + "");

            double moleculeWithinTW = Double.valueOf(workMpa) * Double.valueOf(pipeOD);
            double denominatorWithinTW = 2 * (Double.valueOf(userMpa) * Double.valueOf(pipeCoefficient) / withinI + Double.valueOf(workMpa) * Double.valueOf(cCoefficient));
            double withinTW = moleculeWithinTW / denominatorWithinTW;
            BigDecimal BDwithinTW = new BigDecimal(withinTW).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvWithinTW.setText(BDwithinTW + "");

            double denominatorOutTW = 2 * (Double.valueOf(userMpa) * Double.valueOf(pipeCoefficient) / outI + Double.valueOf(workMpa) * Double.valueOf(cCoefficient));
            double outTW = moleculeWithinTW / denominatorOutTW;
            BigDecimal BDoutTW = new BigDecimal(outTW).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvOutTW.setText(BDoutTW + "");

            if (withinTW < Double.valueOf(withinMin)) {
                tvWithinResult.setText("弯头内侧强度校核合格");
                tvWithinResult.setTextColor(getResources().getColor(R.color.green));
            } else {
                tvWithinResult.setText("弯头内侧强度校核不合格");
                tvWithinResult.setTextColor(getResources().getColor(R.color.red));
            }

            if (outTW < Double.valueOf(outMin)) {
                tvOutResult.setText("弯头外侧强度校核合格");
                tvOutResult.setTextColor(getResources().getColor(R.color.green));
            } else {
                tvOutResult.setText("弯头外侧强度校核不合格");
                tvOutResult.setTextColor(getResources().getColor(R.color.red));
            }

        }
    }

    @OnClick({R.id.ivBack, R.id.tvRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvRight:
                getData();
                break;
        }
    }
}