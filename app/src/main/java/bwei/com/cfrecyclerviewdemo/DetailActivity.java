package bwei.com.cfrecyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import bwei.com.cfrecyclerviewdemo.adapter.MyAdapter;
import bwei.com.cfrecyclerviewdemo.bean.Datas;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RecyclerView rec = (RecyclerView) findViewById(R.id.rec);
        Intent intent = getIntent();
        ArrayList<Datas> list = (ArrayList<Datas>)intent.getSerializableExtra("list");
        MyAdapter myAdapter = new MyAdapter(list,this);
        rec.setLayoutManager(new GridLayoutManager(this, 2));
        rec.setAdapter(myAdapter);
    }
}
