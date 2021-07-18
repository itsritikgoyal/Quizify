package com.example.quizify;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.quizify.SetsActivity.category_id;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView question,quecount,timer;
    private Button option1,option2,option3,option4;
    private List<question> questionList;
    private int questionNo;
    private CountDownTimer count;
    private int score;
    private FirebaseFirestore firebaseFirestore;
    private int setNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = findViewById(R.id.question);
        quecount = findViewById(R.id.indicator);
        timer = findViewById(R.id.countdown);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        setNo = getIntent().getIntExtra("SETNO",1);

        firebaseFirestore = FirebaseFirestore.getInstance();

        getQuestionList();
        score = 0;
    }
    private void getQuestionList(){
        questionList = new ArrayList<>();
        firebaseFirestore.collection("QUIZ").document("CAT"+ category_id)
        .collection("SET"+ setNo).get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots)
                {
                    questionList.add(new question(
                            doc.getString("QUE"),
                            doc.getString("A"),
                            doc.getString("B"),
                            doc.getString("C"),
                            doc.getString("D"),
                            Integer.parseInt(doc.getString("ANS"))));
                }
                setQuestion();
            }
            else
                Toast.makeText(QuestionsActivity.this,(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    @SuppressLint("SetTextI18n")
    private void setQuestion(){
        timer.setText(String.valueOf(10));

        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        quecount.setText(1 +"/"+ questionList.size());

        Starttimer();

        questionNo=0;
    }
    private void Starttimer(){
        count = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        count.start();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        int SelectedOption=0;
        switch (v.getId())
        {
            case R.id.option1:
                SelectedOption=1;
                break;

            case R.id.option2:
                SelectedOption=2;
                break;

            case R.id.option3:
                SelectedOption=3;
                break;

            case R.id.option4:
                SelectedOption=4;
                break;

            default:
        }
        count.cancel();
        checkAnswer(SelectedOption,v);
    }

    private void checkAnswer(int SelectedOption,View view){
        if(SelectedOption == questionList.get(questionNo).getCorrectAns()){
            // Right Ans
            view.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        }
        else {
            // Wrong Ans
            view.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (questionList.get(questionNo).getCorrectAns()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

            }
        }
       changeQuestion();

    }

    @SuppressLint("SetTextI18n")
    private void changeQuestion(){

        if( questionNo < questionList.size() - 1 ){
            questionNo++;

            playanim(question,0,0);
            playanim(option1,0,1);
            playanim(option2,0,2);
            playanim(option3,0,3);
            playanim(option4,0,4);

            quecount.setText((questionNo + 1) +"/"+ questionList.size());

            timer.setText(String.valueOf(10));
            Starttimer();

        }
        else
        {
            // Score
            Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
            intent.putExtra("SCORE", score +"/"+ questionList.size());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //QuestionsActivity.this.finish();
        }
    }
    private void playanim(View view,final int value,int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (value == 0)
                {
                    switch (viewNum)
                    {
                        case 0:
                            ((TextView)view).setText(questionList.get(questionNo).getQuestion());
                            break;
                        case 1:
                            ((Button)view).setText(questionList.get(questionNo).getOptionA());
                            break;
                        case 2:
                            ((Button)view).setText(questionList.get(questionNo).getOptionB());
                            break;
                        case 3:
                            ((Button)view).setText(questionList.get(questionNo).getOptionC());
                            break;
                        case 4:
                            ((Button)view).setText(questionList.get(questionNo).getOptionD());
                            break;
                    }

                    if (viewNum!=0)
                        ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33b5e5")));

                    playanim(view,1,viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        count.cancel();
    }
}
