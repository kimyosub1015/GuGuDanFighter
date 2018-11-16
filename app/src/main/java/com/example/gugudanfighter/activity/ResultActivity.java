package com.example.gugudanfighter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.gugudanfighter.R;

public class ResultActivity extends AppCompatActivity implements ResultSet{

    private TextView RightTime;
    private TextView Alltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RightTime = findViewById(R.id.textViewResultCorrectAnswerCount);
        Alltime = findViewById(R.id.textViewResultQuestionCount);
        int x = ResultSet.i;
        int y = ResultSet.j;
        RightTime.setText(x+"");
        Alltime.setText(""+y);
        findViewById( R.id.buttonResultYes ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity( new Intent( ResultActivity.this, GameActivity.class  ) );
            }
        });

        findViewById( R.id.buttonResultNo ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
