package bwei.com.cfrecyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwei.com.cfrecyclerviewdemo.adapter.MyAdapter;
import bwei.com.cfrecyclerviewdemo.bean.Datas;
import bwei.com.cfrecyclerviewdemo.bean.NetData;
import bwei.com.cfrecyclerviewdemo.utils.OkHttpManager;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Datas> stringList;
    public static Map<Integer, Boolean> map;
    private String urlPath = "http://m.yunifang.com/yunifang/mobile/goods/getall?random=39986&encode=2092d7eb33e8ea0a7a2113f2d9886c90&category_id=17";
    private List<NetData.DataBean> dataList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        TextView main_text1 = (TextView) findViewById(R.id.main_text1);
        TextView main_text2 = (TextView) findViewById(R.id.main_text2);
        TextView main_text3 = (TextView) findViewById(R.id.main_text3);
        Button ok = (Button) findViewById(R.id.ok);
        OkHttpManager.getAsync(urlPath, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                NetData netData = gson.fromJson(result, NetData.class);
                dataList = netData.getData();
                stringList = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    String goods_name = dataList.get(i).getGoods_name();
                    String goods_img = dataList.get(i).getGoods_img();
                    Datas datas = new Datas();
                    datas.goods_name = goods_name;
                    datas.goods_img = goods_img;
                    stringList.add(datas);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new MyAdapter(stringList, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.setRecyclerViewOnItemClickListener(new MyAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        //点击事件
                        //设置选中的项
                        adapter.setSelectItem(position);
                    }

                    @Override
                    public boolean onItemLongClickListener(View view, int position) {
                        //长按事件
                        adapter.setShowBox();
                        //设置选中的项
                        adapter.setSelectItem(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });
            }
        });

        map = new HashMap<>();

        main_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        main_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        main_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    if (m.get(i)){
                        m.put(i, false);
                    }else {
                        m.put(i, true);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Boolean> map = adapter.getMap();
                ArrayList<Datas> list = new ArrayList<>();
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        String goods_name = stringList.get(i).goods_name;
                        String goods_img = stringList.get(i).goods_img;
                        Datas datas = new Datas();
                        datas.goods_name = goods_name;
                        datas.goods_img = goods_img;
                        list.add(datas);
                    }
                }
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                /**
                 * 传值的类型要是ArrayList形式的
                 */
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });
    }
}
