package com.example.openclassroom_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

        private TextView question;
        private Button mPlayButton0;
        private Button mPlayButton1;
        private Button mPlayButton2;
        private Button mPlayButton3;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            question     = (TextView) findViewById(R.id.activity_game_question_text);
            mPlayButton0 = (Button) findViewById(R.id.activity_game_answer1_btn);
            mPlayButton1 = (Button) findViewById(R.id.activity_game_answer2_btn);
            mPlayButton2 = (Button) findViewById(R.id.activity_game_answer3_btn);
            mPlayButton3 = (Button) findViewById(R.id.activity_game_answer4_btn);


            mPlayButton0.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // The user just clicked
                }
            });
            mPlayButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // The user just clicked
                }
            });
            mPlayButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // The user just clicked
                }
            });
            mPlayButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // The user just clicked
                }
            });
        }
}