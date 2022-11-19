package com.example.luke_pipe_check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.util.HideKeyboard;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//直管弯头检测
public class StraightOrElbowActivity extends AppCompatActivity {
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
    @BindView(R.id.spPSelect)
    Spinner spPSelect;
    @BindView(R.id.etRadius)
    EditText etRadius;
    @BindView(R.id.etWithinMin)
    EditText etWithinMin;
    @BindView(R.id.etOutMin)
    EditText etOutMin;
    @BindView(R.id.linStraightTop)
    LinearLayout linStraightTop;
    @BindView(R.id.linElbowTop)
    LinearLayout linElbowTop;
    @BindView(R.id.linStraightBot)
    LinearLayout linStraightBot;
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
    @BindView(R.id.linElbowBot)
    LinearLayout linElbowBot;

    String selectSefect;
    String pipeLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(StraightOrElbowActivity.this, R.color.black);
        setContentView(R.layout.activity_straight_or_elbow);
        ButterKnife.bind(this);
        spinnerSelect();
    }

    /**
     * spinner点击事件
     */
    private void spinnerSelect() {
        ArrayAdapter<String> pSelectAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.pipe));
        spPSelect.setAdapter(pSelectAdapter);
        spPSelect.setSelection(0);
        spPSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.pipe);
                selectSefect = languages[pos];
                if (selectSefect.equals("直管")) {
                    linStraightTop.setVisibility(View.VISIBLE);
                    linStraightBot.setVisibility(View.VISIBLE);
                    linElbowTop.setVisibility(View.GONE);
                    linElbowBot.setVisibility(View.GONE);
                } else if (selectSefect.equals("弯头")) {
                    linStraightTop.setVisibility(View.GONE);
                    linStraightBot.setVisibility(View.GONE);
                    linElbowTop.setVisibility(View.VISIBLE);
                    linElbowBot.setVisibility(View.VISIBLE);
                }
                tvCThickness.setText("");
                tvResult.setText("");
                tvWithinI.setText("");
                tvOutI.setText("");
                tvWithinTW.setText("");
                tvOutTW.setText("");
                tvWithinResult.setText("");
                tvOutResult.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    public void getData() {
        if (selectSefect.equals("直管")) {
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
                double denominator = 2 * (Double.valueOf(userMpa) * Double.valueOf(pipeCoefficient) + Double.valueOf(workMpa) * Double.valueOf(cCoefficient));
                double t = molecule / denominator;
                BigDecimal result = new BigDecimal(t).setScale(6, BigDecimal.ROUND_HALF_UP);
                tvCThickness.setText(result + "");
                if (t < Double.valueOf(minThickness)) {
                    tvResult.setText("直管强度校核合格");
                    tvResult.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tvResult.setText("直管强度校核不合格");
                    tvResult.setTextColor(getResources().getColor(R.color.red));
                }
            }
        }

        if (selectSefect.equals("弯头")) {
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
                    tvWithinResult.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tvWithinResult.setText("弯头内侧强度校核不合格");
                    tvWithinResult.setTextColor(getResources().getColor(R.color.red));
                }

                if (outTW < Double.valueOf(outMin)) {
                    tvOutResult.setText("弯头外侧强度校核合格");
                    tvOutResult.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tvOutResult.setText("弯头外侧强度校核不合格");
                    tvOutResult.setTextColor(getResources().getColor(R.color.red));
                }

            }
        }

    }

    @OnClick({R.id.tvRight, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                getData();
                new HideKeyboard().hideSoftInput(this);
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }
}
