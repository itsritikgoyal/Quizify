package com.example.quizify;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StartActivity extends AppCompatActivity {

    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView appName = findViewById(R.id.appName);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.myanim);
        appName.setAnimation(anim);

        firebaseFirestore = FirebaseFirestore.getInstance();

        new Thread(this::loadData).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private void loadData(){
        catList.clear();
        firebaseFirestore.collection("QUIZ").document("Categories").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        if(doc.exists())
                                {
                                    long count = (long) doc.get("COUNT");
                                    for (int i=1;i<=count;i++){
                                        String catName = doc.getString("CAT"+ i);
                                        catList.add(catName);
                                    }
                                    Intent intent = new Intent(StartActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(StartActivity.this,"No Category Document exists ",Toast.LENGTH_SHORT).show();
                        }
                        StartActivity.this.finish();
                    }
                    else
                    {
                        Toast.makeText(StartActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}