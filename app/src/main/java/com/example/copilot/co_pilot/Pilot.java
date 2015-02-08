package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by thomashay on 2/7/15.
 */
public abstract class Pilot {

    SmsManager manager;

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

    abstract void OnRecv(String from, String contents);
}
