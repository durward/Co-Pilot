package com.example.copilot.co_pilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by jacquelineali on 2/7/15.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;
        String str = "";
        if (bundle != null) {
            Object pdus[] = (Object[]) bundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            for(int i=0; i < messages.length; i++){
                messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "" + messages[i].getOriginatingAddress();
                str += " :";
                str += messages[i].getMessageBody().toString();
                str += "\n";
            }
            // display the message in toast
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

            // display in TextView
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
        }
    }
}
