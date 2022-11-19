package com.example.luke_pipe_check.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.util.AlertDialogUtil;
import com.example.luke_pipe_check.util.StatusBarUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//局部减薄/未焊透
public class LandActivityAuto extends AppCompatActivity {
    @BindView(R.id.spPipeLevel)
    Spinner spPipeLevel;
    @BindView(R.id.spPipeMaterial)
    Spinner spPipeMaterial;
    @BindView(R.id.etPipeThickness)
    EditText etPipeThickness;
    @BindView(R.id.etPipeOD)
    EditText etPipeOD;
    @BindView(R.id.etUserYear)
    EditText etUserYear;
    @BindView(R.id.etNextYear)
    EditText etNextYear;
    @BindView(R.id.etMaxWorkMPa)
    EditText etMaxWorkMPa;
    @BindView(R.id.etDefectLength)
    EditText etDefectLength;
    @BindView(R.id.etMinThickness)
    EditText etMinThickness;
    @BindView(R.id.spSelect)
    Spinner spSelect;
    @BindView(R.id.etNum)
    EditText etNum;
    @BindView(R.id.tvC)
    TextView tvC;
    @BindView(R.id.tvT)
    TextView tvT;
    //    @BindView(R.id.tvPL0)
//    TextView tvPL0;
    @BindView(R.id.tvLevel)
    TextView tvLevel;
    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.relativeLayoutHeader)
    RelativeLayout relativeLayoutHeader;
    @BindView(R.id.etStrength)
    EditText etStrength;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    double D = 0;
    int strength;
    BigDecimal PL0Data = null;
    AlertDialogUtil alertDialogUtil;
    String pipeLeave = "", pipeMaterial = "", select = "";
    String pipeThickness = "", pipeOD = "", userYear = "", maxWorkMPa = "", defectLength = "", minThickness = "";
    double maxWorkMPaData, PL0NumData, defectLengthData, pipeODData, leftOther, CNumData, TNumData, pipeThicknessData, minThicknessData, DifferenceData;
    int num = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new StatusBarUtils().setWindowStatusBarColor(LandActivityAuto.this, R.color.black);
        alertDialogUtil = new AlertDialogUtil(LandActivityAuto.this);
        spinneronCliect();
    }


    private void setEditData() {
        if (etPipeThickness.getText().toString().trim().equals("")) {
            Toast.makeText(this, "管道壁厚不能为空", Toast.LENGTH_SHORT).show();
        } else if (etPipeOD.getText().toString().trim().equals("")) {
            Toast.makeText(this, "管道外径不能为空", Toast.LENGTH_SHORT).show();
        } else if (etUserYear.getText().toString().trim().equals("")) {
            Toast.makeText(this, "已使用年数不能为空", Toast.LENGTH_SHORT).show();
        } else if (etMaxWorkMPa.getText().toString().trim().equals("")) {
            Toast.makeText(this, "最大工作压力不能为空", Toast.LENGTH_SHORT).show();
        } else if (etDefectLength.getText().toString().trim().equals("")) {
            Toast.makeText(this, "缺陷环向长度不能为空", Toast.LENGTH_SHORT).show();
        } else if (etMinThickness.getText().toString().trim().equals("")) {
            Toast.makeText(this, "缺陷附近最小壁厚不能为空", Toast.LENGTH_SHORT).show();
        } else {
            pipeThickness = etPipeThickness.getText().toString().trim();
            pipeOD = etPipeOD.getText().toString().trim();
            userYear = etUserYear.getText().toString().trim();
            maxWorkMPa = etMaxWorkMPa.getText().toString().trim();
            defectLength = etDefectLength.getText().toString().trim();
            minThickness = etMinThickness.getText().toString().trim();
            changeData();
        }
    }

    public void changeData() {
//        tvPL0.setText(String.valueOf(PL0Data) + "");

        maxWorkMPaData = Double.valueOf(maxWorkMPa);
        PL0NumData = Double.valueOf(String.valueOf(PL0Data));
        defectLengthData = Double.valueOf(defectLength);//缺陷环向长度
        pipeODData = Double.valueOf(pipeOD);//管道外径
        leftOther = defectLengthData / pipeODData / 3.141592;
        pipeThicknessData = Double.valueOf(pipeThickness);
        minThicknessData = Double.valueOf(minThickness);

        D = Double.valueOf(pipeOD) / 2;

        if (maxWorkMPaData >= PL0NumData) {
            alertDialogUtil.showSmallDialog("请核对最大压力是否正确");
        } else {
            getLevel(num);
        }
//        if (select.equals("局部减薄")) {
//            DifferenceData = CNumData;
//            getLevel();
//        } else if (select.equals("未焊透")) {
//            if (etNum.getText().toString().trim().equals("")) {
//                Toast.makeText(this, "请输入未焊透值", Toast.LENGTH_SHORT).show();
//            } else {
//                DifferenceData = Double.valueOf(etNum.getText().toString().trim());
//                getLevel();
//            }
//        }
    }


    public void getLevel(int num) {
        CNumData = new BigDecimal((pipeThicknessData - minThicknessData) / Double.valueOf(userYear) * num).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        TNumData = new BigDecimal(minThicknessData - CNumData).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        PL0Data = new BigDecimal(2 / Math.sqrt(3) * strength * Math.log(D / (D - TNumData))).setScale(6, BigDecimal.ROUND_HALF_UP);
        if (pipeLeave.equals("GC2") || pipeLeave.equals("GC3")) {
            int leave = 0;
            for (int i = 1; i < 10; i++) {
                if (leftOther <= 0.25) {
                    if (maxWorkMPaData < PL0NumData * 0.3) {
                        if (0.33 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.33 * TNumData - this.CNumData < 0 && 0.4 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.4 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                            tvLevel.setTextColor(getResources().getColor(R.color.red));
                        }
                    } else if (PL0NumData * 0.3 < maxWorkMPaData && maxWorkMPaData < PL0NumData * 0.5) {
                        if (0.20 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                        }
                        if (0.20 * TNumData - this.CNumData < 0 && 0.25 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                        }
                        if (0.25 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                        }
                    }
                }
                if (0.25 < leftOther && leftOther <= 0.75) {
                    if (maxWorkMPaData < PL0NumData * 0.3) {
                        if (0.25 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.25 * TNumData - this.CNumData < 0 && 0.33 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.33 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                            tvLevel.setTextColor(getResources().getColor(R.color.red));
                        }
                    } else if (PL0NumData * 0.3 < maxWorkMPaData && maxWorkMPaData < PL0NumData * 0.5) {
                        if (0.15 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                        }
                        if (0.15 * TNumData - this.CNumData < 0 && 0.2 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                        }
                        if (0.2 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                        }
                    }
                }
                if (0.75 < leftOther && leftOther <= 1.00){
                    if (maxWorkMPaData < PL0NumData * 0.3) {
                        if (0.2 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.2 * TNumData - this.CNumData < 0 && 0.25 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                            tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                        }
                        if (0.25 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                            tvLevel.setTextColor(getResources().getColor(R.color.red));
                        }
                    } else if (PL0NumData * 0.3 < maxWorkMPaData && maxWorkMPaData < PL0NumData * 0.5) {
                        if (0.15 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("2");
                        }
                        if (0.15 * TNumData - this.CNumData < 0 && 0.2 * TNumData - this.CNumData > 0) {
                            tvLevel.setText("3");
                        }
                        if (0.2 * TNumData - this.CNumData < 0) {
                            tvLevel.setText("4");
                        }
                    }
                }


            }








            if (maxWorkMPaData < PL0NumData * 0.3) {
                if (leftOther <= 0.25) {
                    if (0.40 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                        tvLevel.setTextColor(getResources().getColor(R.color.red));
                    }
                    if (this.DifferenceData < 0.40 * TNumData - this.CNumData && this.DifferenceData >= 0.33 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                        tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                    }
                    if (0.33 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                        tvLevel.setTextColor(getResources().getColor(R.color.theme_color));
                    }
                } else if (0.25 < leftOther && leftOther <= 0.75) {
                    if (0.33 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.33 * TNumData - this.CNumData && this.DifferenceData >= 0.25 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.25 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                } else if (0.75 < leftOther && leftOther <= 1.00) {
                    if (0.25 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.25 * TNumData - this.CNumData && this.DifferenceData >= 0.2 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.2 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                }

            } else if (PL0NumData * 0.3 < maxWorkMPaData && maxWorkMPaData < PL0NumData * 0.5) {
                if (leftOther <= 0.25) {
                    if (0.25 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.25 * TNumData - this.CNumData && this.DifferenceData >= 0.20 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.20 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                } else if (0.25 < leftOther && leftOther <= 0.75 || 0.75 < leftOther && leftOther <= 1.00) {
                    if (0.20 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.20 * TNumData - this.CNumData && this.DifferenceData >= 0.15 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.15 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                }

            }
        } else if (pipeLeave.equals("GC1")) {
            if (maxWorkMPaData < PL0NumData * 0.3) {
                if (leftOther <= 0.25) {
                    if (0.35 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.35 * TNumData - this.CNumData && this.DifferenceData >= 0.30 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.30 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                } else if (0.25 < leftOther && leftOther <= 0.75) {
                    if (0.30 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.30 * TNumData - this.CNumData && this.DifferenceData >= 0.20 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.20 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                } else if (0.75 < leftOther && leftOther <= 1.00) {
                    if (0.20 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.20 * TNumData - this.CNumData && this.DifferenceData >= 0.15 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.15 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                }

            } else if (PL0NumData * 0.3 < maxWorkMPaData && maxWorkMPaData < PL0NumData * 0.5) {
                if (leftOther <= 0.25) {
                    if (0.20 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.20 * TNumData - this.CNumData && this.DifferenceData >= 0.15 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.15 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                } else if (0.25 < leftOther && leftOther <= 0.75 || 0.75 < leftOther && leftOther <= 1.00) {
                    if (0.15 * TNumData - this.CNumData <= this.DifferenceData) {
                        tvLevel.setText("4");
                    }
                    if (this.DifferenceData < 0.15 * TNumData - this.CNumData && this.DifferenceData >= 0.10 * TNumData - this.CNumData) {
                        tvLevel.setText("3");
                    }
                    if (0.10 * TNumData - this.CNumData > this.DifferenceData) {
                        tvLevel.setText("2");
                    }
                }
            }
        }
    }


    /**
     * spinner点击事件
     */
    private void spinneronCliect() {
        ArrayAdapter<String> pipeLevelAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.pipelevel));
        spPipeLevel.setAdapter(pipeLevelAdapter);
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

        ArrayAdapter<String> pipeMaterialAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.pipematerial));
        spPipeMaterial.setAdapter(pipeMaterialAdapter);
        spPipeMaterial.setSelection(0);
        spPipeMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.pipematerial);
                pipeMaterial = languages[pos];
                if (pipeMaterial.equals("20#钢")) {
                    strength = 245;
                } else if (pipeMaterial.equals("奥氏体不锈钢")) {
                    strength = 310;
                } else if (pipeMaterial.equals("16MnR")) {
                    strength = 320;
                }
                etStrength.setText(strength + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        ArrayAdapter<String> selectAdapter = new ArrayAdapter<>(this, R.layout.adapter_item_layout, getResources().getStringArray(R.array.select));
        spSelect.setAdapter(selectAdapter);
        spSelect.setSelection(0);
        spSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.select);
                select = languages[pos];
                if (select.equals("局部减薄")) {
                    etNum.setEnabled(false);
                } else if (select.equals("未焊透")) {
                    etNum.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
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