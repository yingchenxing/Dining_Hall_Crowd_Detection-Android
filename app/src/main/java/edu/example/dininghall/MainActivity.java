package edu.example.dininghall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.example.dininghall.adapter.DiningHallAdapter;
import edu.example.dininghall.pojo.DiningHall;

public class MainActivity extends AppCompatActivity {
    private List<DiningHall> diningHalls;
    private RecyclerView diningRv;
    private DiningHallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();
    }

    private void initAll() {
        //加载todo的recyclerView
        initDiningHalls();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        diningRv = findViewById(R.id.dining_rv);
        diningRv.setLayoutManager(layoutManager);
        adapter = new DiningHallAdapter(diningHalls);
        diningRv.setAdapter(adapter);
    }

    private void initDiningHalls() {
        diningHalls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            diningHalls.add(new DiningHall("dining" + i, i * 5));
        }
    }
}