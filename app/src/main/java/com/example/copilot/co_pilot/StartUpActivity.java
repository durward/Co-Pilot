package com.example.copilot.co_pilot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jacquelineali and Benji Smith on 2/7/15.
 */
public class StartUpActivity extends Activity {
    Button cpLaunchBtn;
    Button mpLaunchBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        cpLaunchBtn = (Button) findViewById(R.id.coPilotLaunchBtn);
        mpLaunchBtn = (Button) findViewById(R.id.pilotLaunchBtn);

        cpLaunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CoPilotActivity.class);
                startActivity(myIntent);
            }
        });

        mpLaunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
