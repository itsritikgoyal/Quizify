package com.example.quizify;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SetsActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    public static int category_id;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        gridView = findViewById(R.id.setsgridview);

        Toolbar toolbar = findViewById(R.id.setstoolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CATEGORY");
        category_id = getIntent().getIntExtra("CATEGORY_ID",1);

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        loadSets();
    }
    public void loadSets()
    {
        Task<DocumentSnapshot> documentSnapshotTask = firestore.collection("QUIZ").document("CAT" + category_id).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        if (doc.exists()) {
                            long sets = (long) doc.get("SETS");
                            SetsAdapter adapter = new SetsAdapter((int) sets);
                            gridView.setAdapter(adapter);
                        } else {
                            Toast.makeText(SetsActivity.this, "No CAT Document exists ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(SetsActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}