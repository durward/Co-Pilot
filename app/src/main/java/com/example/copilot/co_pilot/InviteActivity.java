package com.example.copilot.co_pilot;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Benji Snith on 2/8/2015.
 */
public class InviteActivity extends Activity implements IActivity {

    Button inviteButton;
    EditText inviteNumber;
    SMSListener listener;
    SmsManager manager;
    IntentFilter intentFilter;
    String coPilot = "";
    String potentialCoPilot = "";

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);

        manager = SmsManager.getDefault();

        listener = new SMSListener(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(listener, intentFilter);

        inviteButton = (Button) findViewById(R.id.inviteButton);
        inviteNumber = (EditText) findViewById(R.id.inviteNumber);

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToNumber(inviteNumber.getText().toString(), "MPRQST|");
                Toast requestSent = Toast.makeText(v.getContext(), "Request Sent", Toast.LENGTH_SHORT);
                requestSent.show();
            }
        });
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onReceiveSMS(SmsMessage receivedMsg) {
        String from = receivedMsg.getOriginatingAddress();
        String contents = receivedMsg.getMessageBody();
        if(coPilot.equals("")) {
            if(from.equals(potentialCoPilot) || from.equals("+1" + potentialCoPilot)) {
                System.out.println("This is what you want " + contents.substring(0,7));
                if(contents.substring(0,7).equals("CPRQYS|")) {

                    Toast accepted = Toast.makeText(this, "CoPilot accepted", Toast.LENGTH_SHORT);
                    accepted.show();

                    System.out.println("He said Yes!" + potentialCoPilot + " " + coPilot + "!");
                    coPilot = potentialCoPilot;
                    potentialCoPilot = "";

                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    myIntent.putExtra("com.copilot.copilot", coPilot);
                    startActivity(myIntent);
                }
                else if(contents.substring(0,7).equals("CPRQNO|")) {
                    Toast accepted = Toast.makeText(this, "CoPilot denied", Toast.LENGTH_SHORT);
                    accepted.show();

                    System.out.println("He said.... NOo!" + potentialCoPilot + " " + coPilot + "!");
                }
            }
        }
        else{
            System.out.println("Who dis is?" + coPilot + ": " + from);
        }
    }

    public void SendToNumber(String number, String content){
        System.out.println("Pilot.SendToNumber(): " + number);
        if(number.length() < 10){
            System.out.println("Tried to send message to improper number: " + number);
        }
        else{
            boolean properFormat = true;
            for(int i = 0; i < number.length(); i++){
                int asciiCode = (int)number.charAt(i);
                if((asciiCode < 48 || asciiCode > 57) && (asciiCode != 43 && i == 0)){
                    properFormat = false;
                    break;
                }
            }
            if(properFormat) {
                manager.sendTextMessage(number, null, content, null, null);
            }
            else{
                System.out.println("Tried to send message to improper number: " + number);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
