package com.fuish.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.google.gson.Gson;

public class NewsDetailActivity extends AppCompatActivity {
private Toolbar toolbar;
private WebView mwebView;
    private NewInfo.ResultDTO.DataDTO dataDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar=findViewById(R.id.toolbar);
        mwebView=findViewById(R.id.webview);
        //获取传递的数据
        dataDTO =(NewInfo.ResultDTO.DataDTO)getIntent().getSerializableExtra("DataDTO");

     if(null!=dataDTO){
         toolbar.setTitle(dataDTO.getTitle());
         mwebView.loadUrl(dataDTO.getUrl());

         //添加历史记录
         String dataDTOJson=new Gson().toJson(dataDTO);
         HistoryDbHelper.getInstance(NewsDetailActivity.this).addhistory(null,dataDTO.getUniquekey(),dataDTOJson);

}
     toolbar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        finish();
    }
});
    }
//    //添加历史记录
//    String dataDTOJson=new Gson().toJson(dataDTO);
//    HistoryDbHelper.getInstance(NewsDetailActivity.this).addHistory(null,dataDTO.getUniquekey(),dataDTOJson);



}