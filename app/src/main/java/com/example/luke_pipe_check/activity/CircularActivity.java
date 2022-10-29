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

public class CircularActivity extends AppCompatActivity {
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.etPipeThickness)
    EditText etPipeThickness;
    @BindView(R.id.etPipeThicknessSC)
    EditText etPipeThicknessSC;
    @BindView(R.id.etYear)
    EditText etYear;
    @BindView(R.id.etNextYear)
    EditText etNextYear;
    @BindView(R.id.etCircularRate)
    EditText etCircularRate;
    @BindView(R.id.etCircularDiameter)
    EditText etCircularDiameter;
    @BindView(R.id.tvLevel)
    TextView tvLevel;
    @BindView(R.id.tvFSSL)
    TextView tvFSSL;
    @BindView(R.id.tvTe)
    TextView tvTe;
    @BindView(R.id.tvC)
    TextView tvC;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    /**
     * 圆形缺陷
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(CircularActivity.this, R.color.color_bg_selected);
        setContentView(R.layout.activity_circular);
        ButterKnife.bind(this);
    }

    public void getData() {
        String thickness = etPipeThickness.getText().toString() + "";
        String thicknessSC = etPipeThicknessSC.getText().toString() + "";
        String year = etYear.getText().toString() + "";
        String nextYear = etNextYear.getText().toString() + "";
        String circularRate = etCircularRate.getText().toString() + "";
        String circularDiameter = etCircularDiameter.getText().toString() + "";

        if (thickness.equals("")) {
            Toast.makeText(this, "请输入名义壁厚", Toast.LENGTH_SHORT).show();
        } else if (thicknessSC.equals("")) {
            Toast.makeText(this, "请输入实测壁厚", Toast.LENGTH_SHORT).show();
        } else if (year.equals("")) {
            Toast.makeText(this, "请输入间隔年限", Toast.LENGTH_SHORT).show();
        } else if (nextYear.equals("")) {
            Toast.makeText(this, "请输入下一周期检测年限", Toast.LENGTH_SHORT).show();
        } else if (circularRate.equals("")) {
            Toast.makeText(this, "请输入圆形缺陷率", Toast.LENGTH_SHORT).show();
        } else if (circularDiameter.equals("")) {
            Toast.makeText(this, "请输入圆形缺陷长径", Toast.LENGTH_SHORT).show();
        } else {
            double molecule = Double.valueOf(thickness) - Double.valueOf(thicknessSC);
            double denominator = Double.valueOf(year);
            double fssl = molecule / denominator;
            BigDecimal result = new BigDecimal(fssl).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvFSSL.setText(result + "");

            double c = fssl * Double.valueOf(nextYear);
            BigDecimal C = new BigDecimal(c).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvC.setText(C + "");

            double te = Double.valueOf(thicknessSC) - c;
            BigDecimal Te = new BigDecimal(te).setScale(6, BigDecimal.ROUND_HALF_UP);
            tvTe.setText(Te + "");

            double halfTe = 0.5 * te;
            if (Double.valueOf(circularRate) <= 0.05) {
                if (halfTe <= 6) {
                    if (Double.valueOf(circularDiameter) < halfTe) {
                        tvLevel.setText("2级");
                    } else {
                        tvLevel.setText("4级");
                    }
                } else {
                    if (Double.valueOf(circularDiameter) < 6) {
                        tvLevel.setText("2级");
                    } else {
                        tvLevel.setText("4级");
                    }
                }

            } else {
                tvLevel.setText("4级");
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