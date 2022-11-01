package com.example.luke_pipe_check.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luke_pipe_check.MyApplication;
import com.example.luke_pipe_check.R;


/**
 * 自定义header头部
 * ①返回按钮   返回文字
 * ②title
 * ③保存按钮   保存文字
 *
 */

public class MyTextView extends LinearLayout {
    private AttributeSet attrs;

    private TextView tviewTittle;//标题
    private String tvTitle;//标题
    private ImageView iviewLeft;//图片
    private boolean isTittle;//标题是否显示
    private boolean isleftIv;//图标是否显示

    public void setTvTitle(String tvTitle) {
        tviewTittle.setText(tvTitle);
        this.tvTitle = tvTitle;
    }

    public MyTextView(Context context) {
        super(context);
        initAttributes();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs=attrs;
        initAttributes();
    }

    /**
     * 初始化属性
     */
    private void initAttributes() {
        if(attrs!=null){
            TypedArray typedArray = MyApplication.getContext().obtainStyledAttributes(attrs, R.styleable.myTextView);
            if (typedArray != null) {
                tvTitle = typedArray.getString(R.styleable.myTextView_header_title);
                isTittle=typedArray.getBoolean(R.styleable.myTextView_header_is_title_visiable,true);
                isleftIv=typedArray.getBoolean(R.styleable.myTextView_header_is_left_iv_visiable,true);
                typedArray.recycle();
            }
        }
        initView();
    }

    /**
     * 初始化view
     */
    public void initView(){
        LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_mytextview, this, true);
        tviewTittle= (TextView) findViewById(R.id.tvTitle);
        iviewLeft=(ImageView) findViewById(R.id.ivLeft);
        if(isTittle){
            tviewTittle.setVisibility(VISIBLE);
            tviewTittle.setText(tvTitle);
        }else{
            tviewTittle.setVisibility(GONE);
        }
        if(isleftIv){
            iviewLeft.setVisibility(VISIBLE);
        }else {
            iviewLeft.setVisibility(GONE);
        }
    }
}
