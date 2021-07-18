package com.example.quizify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.start_btn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
            startActivity(intent);
        });
    }
}