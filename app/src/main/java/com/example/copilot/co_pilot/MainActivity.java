package com.example.copilot.co_pilot;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

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
public class MainActivity extends Activity implements IActivity {
    // For sending to co-pilots
    Button grpSendBtn;
    EditText grpMsg;
    EditText grpNums;

    IntentFilter intentFilter;

    // For sending to innocent bystander
    Button passengerSendBtn;
    EditText passengerMsg;
    EditText passengerNum;

    SMSListener smsListener;
    MainPilot mPilot = new MainPilot(this);

    //private BroadcastReceiver intentReceiver = new SMSListener(this);

    public void TestDebugTEST(String debug){
        System.out.println(debug);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsListener = new SMSListener(this);
        this.registerReceiver(smsListener, intentFilter);

//        CoPilot mPilotCast = (CoPilot)mPilot;
//        mPilotCast.SetMainPilot("+4433");

        grpSendBtn = (Button) findViewById(R.id.sendButtonBottom);
        grpMsg = (EditText) findViewById(R.id.messageBottom);
        grpNums = (EditText) findViewById(R.id.numberBottom);

        passengerSendBtn = (Button) findViewById(R.id.sendButtonTop);
        passengerMsg = (EditText) findViewById(R.id.messageTop);
        passengerNum = (EditText) findViewById(R.id.numberTop);


        // When clicked do this: (Bottom)
        grpSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMsg = grpMsg.getText().toString();
                String phoneNumber = grpNums.getText().toString();
                // Calls method that sends messages
                sendGrpMsg(phoneNumber, myMsg);
                grpMsg.setText("");
            }
        });

        // When clicked do this: (Top)
        passengerSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myMsg = passengerMsg.getText().toString();
                String phoneNumber = passengerNum.getText().toString();
                // Calls method that sends messages
                sendPassengerMsg(phoneNumber, myMsg);
                passengerMsg.setText("");
            }
        });
    }

    private void sendGrpMsg(String number, String message) {
        if (mPilot.coPilot.equals("")) {
            mPilot.potentialCoPilot = number;
            mPilot.sendRequest(number);
        }
        else {
            mPilot.SendToCopilot(message);
        }
    }

    private void sendPassengerMsg(String number, String message) {
        mPilot.SetSecondParty("+1" + number);
        mPilot.SendToSecondParty(message);
    }

//    private void sendMsg(String phoneNumber, String myMsg) {
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, myMsg, null, null);
//    }

    public void onReceiveSMS(SmsMessage msg) {
        System.out.println("MA: " + msg.getOriginatingAddress() + " " + msg.getMessageBody());
        mPilot.OnRecv(msg.getOriginatingAddress(), msg.getMessageBody());
    }

    public void displayGrpConversation(String message) {
        // Show message in textview - may need to name these something
        TextView grpDisplay = (TextView) findViewById(R.id.conversationBottom);
        grpDisplay.setText(message);
    }

    public void displayPassengerConversation(String message) {
        // Show message in textview - may need to name these something
        TextView passengerDisplay = (TextView) findViewById(R.id.conversationTop);
        passengerDisplay.setText(message);
    }

    protected void onResume() {
        // register the receiver
        registerReceiver(smsListener, intentFilter);
        super.onResume();
    }

    protected void onPause() {
        // unregister the receiver
        unregisterReceiver(smsListener);
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
