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
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//焊接圆形/条形缺陷
public class CircularOrStripActivity extends AppCompatActivity {
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
    @BindView(R.id.spPSelect)
    Spinner spPSelect;
    @BindView(R.id.spPipeLevel)
    Spinner spPipeLevel;
    @BindView(R.id.linStripLeavel)
    LinearLayout linStripLeavel;
    @BindView(R.id.etPipeOD)
    EditText etPipeOD;
    @BindView(R.id.linStripOD)
    LinearLayout linStripOD;
    @BindView(R.id.linCircular)
    LinearLayout linCircular;
    @BindView(R.id.etStripWidth)
    EditText etStripWidth;
    @BindView(R.id.etStripHeight)
    EditText etStripHeight;
    @BindView(R.id.linStripTop)
    LinearLayout linStripTop;
    @BindView(R.id.linStripBot)
    LinearLayout linStripBot;
    String selectSefect;
    String pipeLeave;

    /**
     * 圆形缺陷
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StatusBarUtils().setWindowStatusBarColor(CircularOrStripActivity.this, R.color.color_bg_selected);
        setContentView(R.layout.activity_circular_or_strip);
        ButterKnife.bind(this);
        spinnerSelect();
    }

    /**
     * spinner点击事件
     */
    private void spinnerSelect() {
        ArrayAdapter<String> pSelectAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.defect));
        spPSelect.setAdapter(pSelectAdapter);
        spPSelect.setSelection(0);
        spPSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.defect);
                selectSefect = languages[pos];
                if (selectSefect.equals("圆形缺陷")) {
                    linStripTop.setVisibility(View.GONE);
                    linStripBot.setVisibility(View.GONE);
                    linCircular.setVisibility(View.VISIBLE);
                }else if (selectSefect.equals("条形缺陷")){
                    linStripTop.setVisibility(View.VISIBLE);
                    linStripBot.setVisibility(View.VISIBLE);
                    linCircular.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        ArrayAdapter<String> spPipeLevelAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.pipelevel));
        spPipeLevel.setAdapter(spPipeLevelAdapter);
        spPipeLevel.setSelection(0);
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

    public void getData() {
        if (selectSefect.equals("圆形缺陷")) {
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

        if (selectSefect.equals("条形缺陷")){
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

    }

    @OnClick({R.id.tvRight, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                getData();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }
}