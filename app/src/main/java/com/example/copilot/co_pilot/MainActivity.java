package com.example.copilot.co_pilot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by durwardbenham3 and jacquelineali on 2/7/15.
 */
public class MainActivity extends Activity{
    // For sending to co-pilots
    Button grpSendBtn;
    EditText grpMsg;
    EditText grpNums;
    TextView grpDisplay;

    IntentFilter intentFilter;

    // For sending to innocent bystander
    Button passengerSendBtn;
    EditText passengerMsg;
    EditText passengerNum;
    TextView passengerDisplay;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Show message in textview - may need to name these something
            TextView grpDisplay = (TextView) findViewById(R.id.conversationBottom);
            grpDisplay.setText(intent.getExtras().getString("sms"));
        }
    };




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot);

        // intent to filter received sms messages
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        grpSendBtn = (Button) findViewById(R.id.sendButtonTop);
        grpMsg = (EditText) findViewById(R.id.messageTop);
        grpNums = (EditText) findViewById(R.id.numberTop);

        passengerSendBtn = (Button) findViewById(R.id.sendButtonBottom);
        passengerMsg = (EditText) findViewById(R.id.messageBottom);
        passengerNum = (EditText) findViewById(R.id.numberBottom);


        // When clicked do this:
        passengerSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMsg = passengerMsg.getText().toString();
                String phoneNumber = passengerNum.getText().toString();

                // Calls method that sends messages
                sendMsg(phoneNumber, myMsg);
            }
        });


    }

    protected void onResume() {
        // register the receiver
        registerReceiver(intentReceiver,intentFilter);
        super.onResume();
    }

    protected void onPause() {
        // unregister the receiver
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

    private void sendMsg(String phoneNumber, String myMsg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, myMsg, null, null);
    }
}
