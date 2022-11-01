package com.example.luke_pipe_check.backups;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 条形缺陷
 */

public class StripActivity extends AppCompatActivity {

    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.spPipeLevel)
    Spinner spPipeLevel;
    @BindView(R.id.etPipeOD)
    EditText etPipeOD;
    @BindView(R.id.etPipeThickness)
    EditText etPipeThickness;
    @BindView(R.id.etPipeThicknessSC)
    EditText etPipeThicknessSC;
    @BindView(R.id.etYear)
    EditText etYear;
    @BindView(R.id.etNextYear)
    EditText etNextYear;
    @BindView(R.id.etStripWidth)
    EditText etStripWidth;
    @BindView(R.id.etStripHeight)
    EditText etStripHeight;
    @BindView(R.id.tvC)
    TextView tvC;
    @BindView(R.id.tvFSSL)
    TextView tvFSSL;
    @BindView(R.id.tvTe)
    TextView tvTe;
    @BindView(R.id.tvLevel)
    TextView tvLevel;
    String pipeLeave = "";
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(StripActivity.this, R.color.color_bg_selected);
        setContentView(R.layout.activity_strip);
        ButterKnife.bind(this);
        spinneronCliect();
    }

    public void getData() {
        String thickness = etPipeThickness.getText().toString() + "";
        String thicknessSC = etPipeThicknessSC.getText().toString() + "";
        String year = etYear.getText().toString() + "";
        String nextYear = etNextYear.getText().toString() + "";
        String stripWidth = etStripWidth.getText().toString() + "";
        String stripHeight = etStripHeight.getText().toString() + "";
        String pipeOD = etPipeOD.getText().toString() + "";

        if (thickness.equals("")) {
            Toast.makeText(this, "请输入名义壁厚", Toast.LENGTH_SHORT).show();
        } else if (thicknessSC.equals("")) {
            Toast.makeText(this, "请输入实测壁厚", Toast.LENGTH_SHORT).show();
        } else if (year.equals("")) {
            Toast.makeText(this, "请输入间隔年限", Toast.LENGTH_SHORT).show();
        } else if (nextYear.equals("")) {
            Toast.makeText(this, "请输入下一周期检测年限", Toast.LENGTH_SHORT).show();
        } else if (stripWidth.equals("")) {
            Toast.makeText(this, "请输入管道条形缺陷自身高度或宽度的最大值", Toast.LENGTH_SHORT).show();
        } else if (stripHeight.equals("")) {
            Toast.makeText(this, "请输入管道条形缺陷总长度", Toast.LENGTH_SHORT).show();
        } else if (pipeOD.equals("")) {
            Toast.makeText(this, "请输入管道外径", Toast.LENGTH_SHORT).show();
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

            if (pipeLeave.equals("GC1")) {
                double halfTe = 0.3 * te;
                if (Double.valueOf(stripWidth) <= halfTe && Double.valueOf(stripWidth) <= 5) {
                    if (Double.valueOf(stripHeight) <= 0.50 * Math.PI * Double.valueOf(pipeOD)) {
                        tvLevel.setText("2级");
                    } else if (Double.valueOf(stripHeight) > 0.50 * Math.PI * Double.valueOf(pipeOD) && Double.valueOf(stripHeight) <= 1.00 * Math.PI * Double.valueOf(pipeOD)) {
                        tvLevel.setText("3级");
                    } else {
                        tvLevel.setText("4级");
                    }
                } else {
                    tvLevel.setText("4级");
                }
            } else {
                double halfTe = 0.35 * te;
                if (Double.valueOf(stripWidth) <= halfTe && Double.valueOf(stripWidth) <= 6) {
                    if (Double.valueOf(stripHeight) <= 0.50 * Math.PI * Double.valueOf(pipeOD)) {
                        tvLevel.setText("2级");
                    } else if (Double.valueOf(stripHeight) > 0.50 * Math.PI * Double.valueOf(pipeOD) && Double.valueOf(stripHeight) <= 1.00 * Math.PI * Double.valueOf(pipeOD)) {
                        tvLevel.setText("3级");
                    } else {
                        tvLevel.setText("4级");
                    }
                } else {
                    tvLevel.setText("4级");
                }
            }
        }
    }

    /**
     * spinner点击事件
     */
    private void spinneronCliect() {
        spPipeLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.pipelevel);
                pipeLeave = languages[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
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