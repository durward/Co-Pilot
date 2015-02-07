package com.example.copilot.co_pilot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by durwardbenham3 and jacquelineali on 2/7/15.
 */
public class PilotActivity extends Activity{
    Button sendBtn;
    EditText message;
    EditText number;

    //
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot);

        sendBtn = (Button) findViewById(R.id.sendButton);
        message = (EditText) findViewById(R.id.message);
        number = (EditText) findViewById(R.id.number);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ADD 2:28
            }
        });


    }
}
