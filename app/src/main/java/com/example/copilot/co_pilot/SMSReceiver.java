package com.example.copilot.co_pilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

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
    }
}
