package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by thomashay on 2/7/15.
 */
public abstract class Pilot {

    SmsManager manager;

    public void SendToNumber(String number, String content){
        System.out.println("Number: " + number);
        manager.sendTextMessage(number, null, content, null, null);
    }

    abstract void OnRecv(String from, String contents);
}
