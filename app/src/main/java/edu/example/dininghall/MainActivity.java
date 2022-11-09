package edu.example.dininghall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.header.StoreHouseHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.example.dininghall.adapter.DiningHallAdapter;
import edu.example.dininghall.common.Result;
import edu.example.dininghall.pojo.DiningHall;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private List<DiningHall> diningHalls;
    private RecyclerView diningRv;
    private DiningHallAdapter adapter;
    private RefreshLayout refreshLayout;
    private final OkHttpClient client = new OkHttpClient();

    @SuppressLint("HandlerLeak")
    private final Handler uiHandler = new Handler() {
        // 覆写这个方法，接收并处理消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new DiningHallAdapter(diningHalls);
                    diningRv.setAdapter(adapter);
                    refreshLayout.finishRefresh();//传入false表示刷新失败
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();

    }

    private void sendRequest() throws IOException {
        String url = "http://10.0.2.2:8080/hall";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("demo", "onResponse:" + body);
//                    Gson gson = new Gson();
//                    DiningHall hall = gson.fromJson(body, DiningHall.class);
//                    Log.d("demo", "onResponse:" + hall);
//                    List<DiningHall> halls = gson.fromJson(body, List.class);
//                    Log.d("demo", "onResponse:" + halls);
                    jsonToList(body);
                    Message msg = new Message();
                    msg.what = 1;
                    uiHandler.sendMessage(msg);
                }
            }

            private void jsonToList(String body) {
                Gson gson = new Gson();
                Type hallType = new TypeToken<List<DiningHall>>() {
                }.getType();
                List<DiningHall> halls = gson.fromJson(body, hallType);
                for (Iterator<DiningHall> iterator = halls.iterator(); iterator.hasNext(); ) {
                    DiningHall hall = (DiningHall) iterator.next();
                    Log.d("demo", "onResponse:" + hall);
                }
                diningHalls = halls;
            }
        });


    }

    private void initActionBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initAll() {
        initActionBar();
        //加载todo的recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        diningRv = findViewById(R.id.dining_rv);
        diningRv.setLayoutManager(layoutManager);
        initDiningHalls();
        adapter = new DiningHallAdapter(diningHalls);
        diningRv.setAdapter(adapter);

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        StoreHouseHeader header = new StoreHouseHeader(this);
        header.initWithString("VT-DINING");
        refreshLayout.setRefreshHeader(header);
//        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    sendRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }


    private void initDiningHalls() {

        diningHalls = new ArrayList<>();
        try {
            sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new DiningHallAdapter(diningHalls);
        diningRv.setAdapter(adapter);
//        for (int i = 0; i < 5; i++) {
//            diningHalls.add(new DiningHall("dining" + i, i * 5));
//        }
//        diningHalls.get(0).setOpen(false);
    }
}