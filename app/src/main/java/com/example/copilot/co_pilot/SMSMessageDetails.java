package com.example.copilot.co_pilot;

//Contains the basic details of
// -who
// -what
// -when

import java.util.Date;

public class SMSMessageDetails {
    String sentFrom;
    Date timeSent;
    String body;

    SMSMessageDetails(String sent, Date time, String msg){
        sentFrom = sent;
        timeSent = time;
        body = msg;
    }
}
