package com.example.luke_pipe_check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class WeldOtherActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.spPipeLevel)
    Spinner spPipeLevel;
    @BindView(R.id.etUndercutGC1)
    EditText etUndercutGC1;
    @BindView(R.id.etUndercutGC2)
    EditText etUndercutGC2;
    @BindView(R.id.linUndercut)
    LinearLayout linUndercut;
    @BindView(R.id.etErrorGC1)
    EditText etErrorGC1;
    @BindView(R.id.etErrorGC2)
    EditText etErrorGC2;
    @BindView(R.id.linError)
    LinearLayout linError;
    @BindView(R.id.linFuse)
    LinearLayout linFuse;
    @BindView(R.id.tvLevel1)
    TextView tvLevel1;
    @BindView(R.id.tvLevel2)
    TextView tvLevel2;
    @BindView(R.id.etThickness)
    EditText etThickness;
    @BindView(R.id.etPipeThickness)
    EditText etPipeThickness;
    @BindView(R.id.etMinThickness)
    EditText etMinThickness;
    @BindView(R.id.etUserYear)
    EditText etUserYear;
    @BindView(R.id.etNextYear)
    EditText etNextYear;
    @BindView(R.id.etOnlyMax)
    EditText etOnlyMax;
    String selectTag;
    String leaveGC1, leaveGC2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weld_other);
        new StatusBarUtils().setWindowStatusBarColor(WeldOtherActivity.this, R.color.color_bg_selected);
        ButterKnife.bind(this);
        spinneronCliect();
    }

    /**
     * spinner点击事件
     */
    private void spinneronCliect() {
        spPipeLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.weld);
                selectTag = languages[pos];
                if (selectTag.equals("咬边")) {
                    linUndercut.setVisibility(View.VISIBLE);
                    linError.setVisibility(View.GONE);
                    linFuse.setVisibility(View.GONE);
                } else if (selectTag.equals("错边")) {
                    linUndercut.setVisibility(View.GONE);
                    linError.setVisibility(View.VISIBLE);
                    linFuse.setVisibility(View.GONE);
                } else if (selectTag.equals("未熔合")) {
                    linUndercut.setVisibility(View.GONE);
                    linError.setVisibility(View.GONE);
                    linFuse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void setEditData() {
        leaveGC1 = "";
        leaveGC2 = "";
        if (selectTag.equals("咬边")) {
            if (etUndercutGC1.getText().toString().equals("") && etUndercutGC2.getText().toString().equals("")) {
                Toast.makeText(this, "请输入管道咬边深度", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String GC1UndercutData = etUndercutGC1.getText().toString();
                if (!GC1UndercutData.equals("")) {
                    if (Double.valueOf(GC1UndercutData) > 0.5) {
                        leaveGC1 = getResources().getString(R.string.influence);
                        tvLevel1.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        leaveGC1 = getResources().getString(R.string.no_influence);
                        tvLevel1.setTextColor(getResources().getColor(R.color.black));
                    }
                    tvLevel1.setText("GC1:" + leaveGC1);
                }
                String GC2UndercutData = etUndercutGC2.getText().toString();
                if (!GC2UndercutData.equals("")) {
                    if (Double.valueOf(GC2UndercutData) > 0.8) {
                        leaveGC2 = getResources().getString(R.string.influence);
                        tvLevel2.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        leaveGC2 = getResources().getString(R.string.no_influence);
                        tvLevel2.setTextColor(getResources().getColor(R.color.black));
                    }
                    tvLevel2.setText("GC2/3:" + leaveGC2);
                }
            }
        } else if (selectTag.equals("错边")) {
            if (etErrorGC1.getText().toString().equals("") && etErrorGC2.getText().toString().equals("")) {
                Toast.makeText(this, "请输入管道外壁错边量", Toast.LENGTH_SHORT).show();
                return;
            } else if (etThickness.getText().toString().equals("")) {
                Toast.makeText(this, "请输入工程壁厚", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String GC1ErrorData = etErrorGC1.getText().toString();
                String ThicknessData = etThickness.getText().toString();
                if (!GC1ErrorData.equals("")) {
                    if (Double.valueOf(GC1ErrorData) > 3 && Double.valueOf(GC1ErrorData) >= Double.valueOf(ThicknessData) * 0.2) {
                        leaveGC1 = getResources().getString(R.string.there_leave);
                        tvLevel1.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        leaveGC1 = getResources().getString(R.string.two_leave);
                        tvLevel1.setTextColor(getResources().getColor(R.color.black));
                    }
                    tvLevel1.setText("GC1:" + leaveGC1);
                }
                String GC2UndercutData = etErrorGC2.getText().toString();
                if (!GC2UndercutData.equals("")) {
                    if (Double.valueOf(GC2UndercutData) >= 3 && Double.valueOf(GC2UndercutData) >= Double.valueOf(ThicknessData) * 0.25) {
                        leaveGC2 = getResources().getString(R.string.there_leave);
                        tvLevel2.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        leaveGC2 = getResources().getString(R.string.two_leave);
                        tvLevel2.setTextColor(getResources().getColor(R.color.black));
                    }
                    tvLevel2.setText("GC2/3:" + leaveGC2);
                }
            }
        } else if (selectTag.equals("未熔合")) {
            if (etPipeThickness.getText().toString().equals("")) {
                Toast.makeText(this, "请输入上次定期检验缺陷附近壁厚实测值或名义壁厚", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etMinThickness.getText().toString().equals("")) {
                Toast.makeText(this, "请输入本次定期检验缺陷附近壁厚实测最小值", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etUserYear.getText().toString().equals("")) {
                Toast.makeText(this, "请输入两次定期检验间隔年限或首次定检年限", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etNextYear.getText().toString().equals("")) {
                Toast.makeText(this, "请输入预测下一周期年限", Toast.LENGTH_SHORT).show();
                return;
            }
            String pipeThickness = etPipeThickness.getText().toString().trim();
            String minThickness = etMinThickness.getText().toString().trim();
            String userYear = etUserYear.getText().toString().trim();
            String nextYear = etNextYear.getText().toString().trim();
            BigDecimal CData = new BigDecimal((Double.valueOf(pipeThickness) - Double.valueOf(minThickness)) / Double.valueOf(userYear) * Double.valueOf(nextYear)).setScale(3, BigDecimal.ROUND_HALF_UP);
            BigDecimal TData = new BigDecimal(Double.valueOf(minThickness) - Double.valueOf(String.valueOf(CData))).setScale(6, BigDecimal.ROUND_HALF_UP);
            double teData = TData.doubleValue();
            double onlyMax = 0;
            if (!etOnlyMax.getText().toString().trim().equals("")){
                onlyMax = Double.valueOf(etOnlyMax.getText().toString().trim());
            }

            if (teData<2.5&&onlyMax!=0){
                tvLevel1.setText(R.string.four_leave);
                tvLevel1.setTextColor(getResources().getColor(R.color.red));
            }
            if (teData>=2.5&&teData<4){
                if (onlyMax<=0.15*teData && onlyMax<=0.5){
                    tvLevel1.setText(R.string.no_influence);
                    tvLevel1.setTextColor(getResources().getColor(R.color.black));
                }else {
                    tvLevel1.setText(R.string.four_leave);
                    tvLevel1.setTextColor(getResources().getColor(R.color.red));
                }
            }
            if (teData>=4&&teData<8){
                double twoLeaveData = 0.15*teData < 1 ? 0.15*teData :1;
                double threeLeaveData = 0.20*teData < 1.5 ? 0.20*teData :1.5;
                leaveContrast(onlyMax,twoLeaveData,threeLeaveData);
            }
            if (teData>=8&&teData<12){
                double twoLeaveData = 0.15*teData < 1.5 ? 0.15*teData : 1.5;
                double threeLeaveData = 0.20*teData < 2.0 ? 0.20*teData : 2.0;
                leaveContrast(onlyMax,twoLeaveData,threeLeaveData);
            }
            if (teData>=12&&teData<20){
                double twoLeaveData = 0.15*teData < 2.0 ? 0.15*teData : 2.0;
                double threeLeaveData = 0.20*teData < 3.0 ? 0.20*teData : 3.0;
                leaveContrast(onlyMax,twoLeaveData,threeLeaveData);
            }
            if (teData>=20){
                if (onlyMax<=3.0){
                    tvLevel1.setText(R.string.two_leave);
                    tvLevel1.setTextColor(getResources().getColor(R.color.black));
                }else {
                    double threeLeaveData = 0.20*teData < 5.0 ? 0.20*teData : 5.0;
                    if (onlyMax<=threeLeaveData){
                        tvLevel1.setText(R.string.there_leave);
                        tvLevel1.setTextColor(getResources().getColor(R.color.black));
                    }else {
                        tvLevel1.setText(R.string.four_leave);
                        tvLevel1.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        }
    }

    private void leaveContrast(double onlyMax, double twoLeaveData, double threeLeaveData){
        if (onlyMax<=twoLeaveData){
            tvLevel1.setText(R.string.two_leave);
            tvLevel1.setTextColor(getResources().getColor(R.color.black));
        }else if (onlyMax<=threeLeaveData){
            tvLevel1.setText(R.string.there_leave);
            tvLevel1.setTextColor(getResources().getColor(R.color.black));
        }else if (onlyMax>threeLeaveData){
            tvLevel1.setText(R.string.four_leave);
            tvLevel1.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @OnClick({R.id.tvRight, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                setEditData();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }
}