package com.example.jardosh.brainteaser;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestion = 0;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gamePage;


    //Play Again Code
    public void playAgain(final View view){
        generateNewQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        score = 0;
        numberOfQuestion= 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");

        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");

            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score)+"/"+Integer.toString(numberOfQuestion));
                playAgainButton.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        }.start();

    }




    //Generate New Questions
    public void generateNewQuestion(){
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + "+" + Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);


        int incorrectAnswer;

                answers.clear();


        for(int i = 0;i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == a+b){
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    //Choose the correct Answer
    public void chooseAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Log.i("CORRECT", String.valueOf(answers.get(locationOfCorrectAnswer)));
            score++;
            resultTextView.setText("Correct!");


        }else{
            Log.i("WRONG", String.valueOf(answers.get(locationOfCorrectAnswer)));
            resultTextView.setText("Wrong!!!");
        }

        numberOfQuestion++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestion));
        generateNewQuestion();
    }

    public void onStart(View view){
        startButton.setVisibility(View.INVISIBLE);
        gamePage.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gamePage = (RelativeLayout)findViewById(R.id.gamePage);


    }
}
