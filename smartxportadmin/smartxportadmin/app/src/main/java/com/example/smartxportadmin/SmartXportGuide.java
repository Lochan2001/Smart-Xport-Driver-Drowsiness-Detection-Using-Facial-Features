package com.example.smartxportadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

public class SmartXportGuide extends AppCompatActivity {
    ExpandableListView expandableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_xport_guide);

        expandableTextView=findViewById(R.id.eTV);
        ExpandableTextviewAdapter adapter= new ExpandableTextviewAdapter(SmartXportGuide.this);
        expandableTextView.setAdapter(adapter);
    }
}