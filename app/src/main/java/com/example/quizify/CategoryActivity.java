package com.example.quizify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import static com.example.quizify.StartActivity.catList;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView gridView = findViewById(R.id.gridview);

        CategoryAdapter adapter = new CategoryAdapter(catList);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home )
        {
            CategoryActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}