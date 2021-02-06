package com.example.openclassroom_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //@Override
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mPlayButton2;
    Name nameobject;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mPlayButton2 = (Button) findViewById(R.id.activity_main_camera_btn);

        //Le boutton n'est plus disponible
        mPlayButton.setEnabled(false);
        nameobject = new Name();


        mNameInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is where we'll check the user input
                mPlayButton.setEnabled(s.toString().length() != 0);
//                nameobject.setmFirstName((String) s);

            }

            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivity);

                nameobject.setmFirstName(mNameInput.getText().toString());
                // The user just clicked
            }
        });




        mPlayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CameraActivity = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(CameraActivity);
                // The user just clicked
            }
        });

    }
}
