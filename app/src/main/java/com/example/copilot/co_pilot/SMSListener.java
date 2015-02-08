package com.example.copilot.co_pilot;

/**
 * Created by Benji Snith on 2/7/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSListener extends BroadcastReceiver
{
    MainActivity activity;

    SMSListener(MainActivity mainAc){
        super();

        activity = mainAc;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("On Receive!");
        activity.TestDebugTEST("Yo, is it working yet?");

        Bundle bundle = intent.getExtras();
        SmsMessage[] recievedMsgs = null;
        String str = "";
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            recievedMsgs = new SmsMessage[pdus.length];
            for (int i=0; i < pdus.length; i++) {
                recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                activity.onReceiveSMS(recievedMsgs[i]);
                str += "SMS from " + recievedMsgs[i].getOriginatingAddress() + " :" + recievedMsgs[i].getMessageBody();
            }
        }

        System.out.println("TXT MSG: " + str);

        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
