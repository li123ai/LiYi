package LiYi.com.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import LiYi.com.HttpProxy;
import LiYi.com.NetInputUtils;
import LiYi.com.R;
import LiYi.com.bean.VideoInfo;
import LiYi.com.bean.VideoListResponse;
import LiYi.com.lsit.VideoAdapter;

public class ListHeadActivity extends AppCompatActivity {

    private static final int Resquset_CODE_EDT=10;
    private VideoAdapter mAdapter;
    private Handler mHandler=new Handler();
    private long firstTime = 0;
    private ListView mListView;
    private TextView mTextname;
    private TextView  mSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_head);

        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏动作栏
        getSupportActionBar().hide();

        mListView=findViewById(R.id.lv);

        View headLayout=buildListHeader();

        mTextname=headLayout.findViewById(R.id.nameText);
        mSign=headLayout.findViewById(R.id.tagText);

        mListView.addHeaderView(headLayout);



//        列表
        /*列表展现三个要素ListView 数据源 Adapter*/
        mListView=findViewById(R.id.lv);
        /*数据源*/
        final List<VideoInfo> dataList=new ArrayList<>();
        mAdapter=new VideoAdapter(dataList,this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    VideoInfo videoInfo=dataList.get(position-1);
                    Intent intent=new Intent(ListHeadActivity.this,WebActivity.class);
                    intent.putExtra(WebActivity.WEB_URL,videoInfo.getFilePath());
                    startActivity(intent);
                }
            }
        });
        initData();
    }

    private View buildListHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.layout_header,null);
        TextView signTV = headLayout.findViewById(R.id.tagText);
        signTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListHeadActivity.this,"去设置签名",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListHeadActivity.this, TagActivity.class);
                startActivityForResult(intent,Resquset_CODE_EDT);
                /*finish();*/
            }
        });

        return headLayout;
    }

    //    获取签到页面的签名
    /*获取签到页面的数据*/
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case Resquset_CODE_EDT:{
                if(resultCode==RESULT_OK){
                    String message=data.getStringExtra("massage");
                    mSign.setText(message);
                }else {
                    Toast.makeText(this,"不写签名真的好吗？",Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
    //    列表
    private void initData() {
        String raUrl="http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(raUrl, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                String respJson=null;
                try {
                    respJson= NetInputUtils.readString(inputStream);
                    VideoListResponse videoListResponse=converJsonToBean(respJson);
                    final List<VideoInfo> list=videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            /*刷新Adapter的UI*/
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent=getIntent();
        mTextname.setText(intent.getStringExtra("name"));
    }

    private VideoListResponse converJsonToBean(String json) {
        Gson gson=new Gson();
        VideoListResponse response=gson.fromJson(json,VideoListResponse.class);
        return  response;
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(ListHeadActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }
}
