package com.example.copilot.co_pilot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by durwardbenham3 on 2/7/15.
 */
public class PilotActivity extends Activity{
    Button send;
    EditText message;
    EditText number;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot);

        send = (Button) findViewById(R.id.send);

    }
}
