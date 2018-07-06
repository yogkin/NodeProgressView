package com.nodeprogress.nodeprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button2).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,LogistcsNodeActivity.class)));
        findViewById(R.id.button3).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,BaseNodeActivity.class)));
    }
}
