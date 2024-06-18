package com.fuish.test.Adapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuish.test.NewInfo;
import com.fuish.test.NewsDetailActivity;
import com.fuish.test.NewsListAdapter;
import com.fuish.test.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TabNewsFragment extends Fragment {
    private String url="http://v.juhe.cn/toutiao/index?key=b394cae65f853553d05619ee6abf570e&type=top&page=20&page_size=&is_filter=";

    private static final String ARG_PARAM = "title";
    private View rootView;
    private RecyclerView recyclerView;
    private NewsListAdapter mNewsListAdapter;
    private String title;


  private Handler mHandler=new Handler(Looper.myLooper()){
      @Override
      public void handleMessage(@NonNull Message msg) {
          super.handleMessage(msg);
          if (msg.what == 100) {
                String data = (String) msg.obj;
                NewInfo newsInfo = new Gson().fromJson(data, NewInfo.class);
                //刷新适配器
                if (null!=mNewsListAdapter){
                    mNewsListAdapter.setListData(newsInfo.getResult().getData());
                }
      }
  } };

    public TabNewsFragment() {

    }


    public static TabNewsFragment newInstance(String param) {
        TabNewsFragment fragment = new TabNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_tab_news, container, true);
        recyclerView=rootView.findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNewsListAdapter=new NewsListAdapter(getActivity());
        recyclerView.setAdapter(mNewsListAdapter);
        getHttpData();
        mNewsListAdapter.setOnItemClickListener(new NewsListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(NewInfo.ResultDTO.DataDTO dataDTO, int position) {
                //跳转到详情页
                Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("DataDTO",dataDTO);
                startActivity(intent);

            }
        });
    }

    private void getHttpData() {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+title)
                .get()
                .build();
                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("-------------", "onFailure: "+e.toString());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String data = response.body().string();
                        Message message = new Message();
                        //指定一个标识符
                        message.what = 100;
                        message.obj = data;
                        mHandler.sendMessage(message);
                    }
                });

    }
}