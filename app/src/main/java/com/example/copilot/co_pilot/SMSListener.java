package com.example.copilot.co_pilot;

/**
 * Created by Benji Snith on 2/7/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSListener extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.print("On Receive!");

        Bundle bundle = intent.getExtras();
        SmsMessage[] recievedMsgs = null;
        String str = "";
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            recievedMsgs = new SmsMessage[pdus.length];
            for (int i=0; i < pdus.length; i++) {
                recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += "SMS from " + recievedMsgs[i].getOriginatingAddress() + " :" + recievedMsgs[i].getMessageBody().toString();
            }
        }

        System.out.println("TXT MSG: " + str);

        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
