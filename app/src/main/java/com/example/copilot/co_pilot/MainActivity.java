package com.example.copilot.co_pilot;

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

    // For sending to innocent bystander
    Button passengerSendBtn;
    EditText passengerMsg;
    EditText passengerNum;
    TextView passengerDisplay;

    SMSListener smsListener;
    Pilot mPilot = new CoPilot(this);

    public void TestDebugTEST(String debug){
        System.out.println(debug);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilot);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsListener = new SMSListener(this);
        this.registerReceiver(smsListener, intentFilter);
        CoPilot mPilotCast = (CoPilot)mPilot;
        mPilotCast.SetMainPilot("+4433");

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

    private void sendMsg(String phoneNumber, String myMsg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, myMsg, null, null);
    }

    public void onRecieveSMS(SmsMessage msg) {
        mPilot.OnRecv(msg.getOriginatingAddress(), msg.getMessageBody());
    }

    //If since is null, then all messages are returned
    public SMSMessageDetails[] GetMessages(String number, Date since){
        Uri sentURI = Uri.parse("content://sms/inbox");
        String[] reqCols = new String[] { "_id", "address", "body", "date"};
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(sentURI, reqCols, null, null, null);

        if(c!=null && c.getCount()>0){
            c.moveToFirst();

            //SMSMessageDetails[] messages = new SMSMessageDetails[c.getCount()];
            ArrayList<SMSMessageDetails> messages = new ArrayList<SMSMessageDetails>();

            for(int i = 0; i < c.getCount(); i++) {
                String addr = c.getString(c.getColumnIndex("address"));
                String bod = c.getString(c.getColumnIndex("body"));
                Long dateLong = c.getLong(c.getColumnIndex("date"));
                Date time = new Date(dateLong);

                if(addr.equals(number) && (since == null || since.compareTo(time) <= 0)) {
                    messages.add(new SMSMessageDetails(addr, time, bod));
                }
                c.moveToNext();
            }

            c.close();
            SMSMessageDetails[] messagesArr = new SMSMessageDetails[messages.size()];
            messages.toArray(messagesArr);
            return messagesArr;
        }

        else{
            if(!c.isClosed()) {
                c.close();
            }
            return new SMSMessageDetails[0];
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.unregisterReceiver(smsListener);
    }
}
