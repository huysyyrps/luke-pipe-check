package com.example.luke_pipe_check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.luke_pipe_check.R;
import com.example.luke_pipe_check.bean.DataBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<DataBean, ImageAdapter.ViewHolder> {
    List<DataBean> mDatas;
    Context context;
    private View view;
    public ImageAdapter(List<DataBean> mDatas, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindView(ViewHolder holder, DataBean data, int position, int size) {
        Glide.with(context).load(data.getImageRes()).into(holder.imageView);
    }

    //定义ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
