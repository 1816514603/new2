package com.fuish.test.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fuish.test.NewInfo;
import com.fuish.test.R;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyHolder> {
        private List<NewInfo.ResultDTO.DataDTO> mDataBeanList = new ArrayList<>();
        private Context mContext;


        public NewsListAdapter(Context context) {
            this.mContext = context;
        }


        /**
         * 为adapter 设置数据源
         */
        public void setListData(List<NewInfo.ResultDTO.DataDTO> listData) {
            this.mDataBeanList = listData;
            //一定要调用
            notifyDataSetChanged();

        }



        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //加载布局文件
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item, null);
            return new MyHolder(view);
        }

        @Override
        public void  onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
            NewInfo.ResultDTO.DataDTO dataDTO = mDataBeanList.get(position);

            //设置数据
            holder.author_name.setText("来源："+dataDTO.getAuthor_name());
            holder.title.setText(dataDTO.getTitle());
            holder.date.setText(dataDTO.getDate());
            //加载图片
            Glide.with(mContext).load(dataDTO.getThumbnail_pic_s()).error(R.mipmap.img_error).into(holder.thumbnail_pic_s);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(null!=mOnItemClickListener){
            mOnItemClickListener.onItemClick(dataDTO,position);
        }
    }
});

        }

        @Override
        public int getItemCount() {
            return mDataBeanList.size();
        }

        static class MyHolder extends RecyclerView.ViewHolder {

            ImageView thumbnail_pic_s;
            TextView title;
            TextView author_name;
            TextView date;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                thumbnail_pic_s = itemView.findViewById(R.id.thumbnail_pic_s);
                title = itemView.findViewById(R.id.title);
                author_name = itemView.findViewById(R.id.author_name);
                date = itemView.findViewById(R.id.date);

            }
        }
private  onItemClickListener mOnItemClickListener;



    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
            void onItemClick(NewInfo.ResultDTO.DataDTO dataDTO,int position);
        }
    }

