package com.example.gugudanfighter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.gugudanfighter.R;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private static final int TIME_LIMIT = 30;

    private Timer timer = new Timer();

    private int correctAnswer;//정답
    private int answerMix;
    private int correctAnswerCount = 0; //정답수
    private int questionCount = 0; //출제수

    public static int correctAC;
    public static int questionCo;

    private TextView tvLastTime;//남은시간
    private TextView tvScore;//점수
    private TextView tvX;//왼쪽값
    private TextView tvY;//오른쪽값
    private TextView Scores;

    private int[] answerButtonIds = {
            R.id.button_0_0, R.id.button_0_1, R.id.button_0_2,
            R.id.button_1_0, R.id.button_1_1, R.id.button_1_2,
            R.id.button_2_0, R.id.button_2_1, R.id.button_2_2
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //View 찾기
        tvLastTime = findViewById(R.id.textViewLastTime);// 화면에 남은 시간을
        //여기서부터 숫자표기
        // UI 초기화
        uiset();
        updateLastTime(TIME_LIMIT); // TIME_LIMIT(30초)를 표기
        // 타이머 시작
        timer.schedule(new GamePlayTimerTask(), 1000, 1000);
    }
    private void uiset(){
        int x = randomize(1, 9);
        int y = randomize(1, 9);
        correctAnswer = x * y;
        int t[] = new int[9];
        answerMix = correctAnswer - 3;
        if (answerMix < 1) {
            answerMix = correctAnswer;
        }
        //버튼에 숫자를 올려준다
        for (int i = 0; i < 9; i++) {
            t[i] = randomize(answerMix, 9);
            Button bx = findViewById(answerButtonIds[i]);
            for (int j = 0; j < i; j++) {
                if (i == 0) {
                }
                else if (i != 0 && t[i] == t[j]) {
                    i--;
                }
            }
            bx.setText("" + t[i]);
        }
        //랜덤으로 숫자를 가져와서 버튼에 숫자올리기 끝
        //x,y값 랜덤으로 불러오기
        tvX = findViewById(R.id.textViewLeftOperand);//X값
        tvY = findViewById(R.id.textViewRightOperand);//X값
        tvX.setText("" + x);
        tvY.setText("" + y);
        //Button bx2 = findViewById(answerButtonIds[i]);
        //bx2.getText().toString();
    }
    private int randomize(int from, int to) {
        return (int) (Math.random() * to) + from;
    }
    private void updateScore(int plus, int set) {
        correctAnswerCount = plus;
        questionCount = set;
        Scores = findViewById(R.id.textViewScore);//X값
        Scores.setText(correctAnswerCount+"/"+questionCount);
    }
    private void updateLastTime(int lastTime) {
        tvLastTime.setText("" + lastTime);
    }
    public void checkAnswer(View v) {
        int index = -1;
        for (int i = 0; i < answerButtonIds.length; i++) {
            if(v.getId() == answerButtonIds[i]) {
                index = i;
                String caString = String.valueOf(correctAnswer);
                Button bx = findViewById(answerButtonIds[index]);
                String abString= bx.getText().toString();
                if(caString.equals(abString)){
                    //Toast.makeText(getApplicationContext(),"정답입니다.",Toast.LENGTH_SHORT).show();
                    updateScore(correctAnswerCount+1,questionCount+1);
                    uiset();
                }
                else{
                    //Toast.makeText(getApplicationContext(),"아닌데요? 너 고소!",Toast.LENGTH_SHORT).show();
                    updateScore(correctAnswerCount,questionCount+1);
                    uiset();
                }
                break;
            }
        }
        if (index > -1) {
        }
    }
    private class GamePlayTimerTask extends TimerTask {
        private int seconds = 0;

        @Override
        public void run() {
            if (seconds >= TIME_LIMIT) {
                /* 게임종료 */
                //1. 타이머 스톱
                timer.cancel();
                //2. Activity 종료
                correctAC = correctAnswerCount;
                questionCo = questionCount;
                finish();
                //3. Result Activity 이동
                startActivity( new Intent( GameActivity.this, ResultActivity.class  ) );
                return;
            }
            seconds++;
            // UI 변경은 메인쓰레드에서 해야 함.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateLastTime(TIME_LIMIT - seconds);
                }
            });
        }
    }
}