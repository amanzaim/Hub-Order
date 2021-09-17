package com.example.huborder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recycleAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ArrayList<History> exampleList = new ArrayList<>();
        exampleList.add(new History("Pak Teh Claypot & Sizzling", "Sizzling Chicken Yee Mee with Egg", "RM7"));
        exampleList.add(new History("Pak Teh Claypot & Sizzling", "Sizzling Chicken Rice with Egg", "RM7"));
        exampleList.add(new History("Pak Teh Claypot & Sizzling", "Sizzling Beef Rice with Egg", "RM8"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleAdapter = new hisAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }
}