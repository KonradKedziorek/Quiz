package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "Main activity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static  final String KEY_EXTRA_ANSWER = "com.example.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean answerWasShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        tipButton = findViewById(R.id.tip_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });

        setNextQuestion();

        tipButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

    }

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Button tipButton;

    private Question[] questions = new Question[]{
            new Question(R.string.quest_1, true),
            new Question(R.string.quest_2, false),
            new Question(R.string.quest_3, true),
            new Question(R.string.quest_4, true),
            new Question(R.string.quest_5, false),
            new Question(R.string.quest_6, false),
            new Question(R.string.quest_7, false),
            new Question(R.string.quest_8, true),
    };

    private int currentIndex = 0;

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else {
            if (userAnswer == correctAnswer){
                resultMessageId = R.string.correct_answer;
            }else{
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metodacyklu życia: onResume");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {return;}
        if (requestCode == REQUEST_CODE_PROMPT){
            if(data == null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}