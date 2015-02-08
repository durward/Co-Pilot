package com.example.copilot.co_pilot;

/**
 * Created by jacquelineali and Benji Smith on 2/7/15.
 */

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;

import android.content.ContentResolver;
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

public class CoPilotActivity extends Activity implements IActivity {
    // For sending to group msg
    Button grpSendBtn;
    EditText grpMsg;
    EditText grpNums;

    IntentFilter intentFilter;

    SMSListener smsListener;
    CoPilot coPilot = new CoPilot(this);

    //private BroadcastReceiver intentReceiver = new SMSListener(this);

    public void TestDebugTEST(String debug){
        System.out.println(debug);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copilot);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsListener = new SMSListener(this);
        this.registerReceiver(smsListener, intentFilter);

//        CoPilot mPilotCast = (CoPilot)mPilot;
//        mPilotCast.SetMainPilot("+4433");

        grpSendBtn = (Button) findViewById(R.id.sendButtonBottom);
        grpMsg = (EditText) findViewById(R.id.messageBottom);
        grpNums = (EditText) findViewById(R.id.numberBottom);

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

        grpMsg.setText("");

    }

    private void sendGrpMsg(String number, String message) {
        coPilot.SendToMainPilot(message);
    }

    public void handleRequest(final String requester) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Accept Request?");

        // set dialog message
        alertDialogBuilder
                .setMessage(requester + " needs a co-pilot!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        System.out.println("I said yes");
                        coPilot.SetMainPilot(requester);
                        System.out.println(coPilot.mainPilot);
                        coPilot.SendToNumber(requester, "CPRQYS|");
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        System.out.println("I said no");
                        coPilot.SendToNumber(requester, "CPRQNO|");
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void onReceiveSMS(SmsMessage msg) {
        System.out.println("MA: " + msg.getOriginatingAddress() + " " + msg.getMessageBody());
        coPilot.OnRecv(msg.getOriginatingAddress(), msg.getMessageBody());
    }

    public void displayGrpLeftConversation(String message) {
        // Show message in textview - may need to name these something
        TextView grpDisplay = (TextView) findViewById(R.id.conversationBottomLeft);
        String prev = grpDisplay.getText().toString();
        grpDisplay.setText(prev + "\n" + message);
    }

    public void displayGrpRightConversation(String message) {
        // Show message in textview - may need to name these something
        TextView grpDisplay = (TextView) findViewById(R.id.conversationBottomRight);
        String prev = grpDisplay.getText().toString();
        grpDisplay.setText(prev + "\n" + message);
    }

    public void displayPassengerLeftConversation(String message) {
        // Show message in textview - may need to name these something
        TextView passengerDisplay = (TextView) findViewById(R.id.conversationTopLeft);
        String prev = passengerDisplay.getText().toString();
        passengerDisplay.setText(prev + "\n" + message);
    }

    public void displayPassengerRightConversation(String message) {
        // Show message in textview - may need to name these something
        TextView passengerDisplay = (TextView) findViewById(R.id.conversationTopRight);
        String prev = passengerDisplay.getText().toString();
        passengerDisplay.setText(prev + "\n" + message);
    }

    protected void onResume() {
        // register the receiver
        registerReceiver(smsListener,intentFilter);
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
