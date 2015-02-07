package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by Benji Snith on 2/7/2015.
 */

public class MainPilot extends Pilot{

    String secondParty = "";
    String coPilot = "";

 MainActivity activity;

    MainPilot(){
        super.manager = SmsManager.getDefault();
    }

    MainPilot(MainActivity mainAc){
        super.manager = SmsManager.getDefault();
        activity = mainAc;
    }

    public void SetCopilot(String newNumber){
        coPilot = newNumber;
    }

    public void SetSecondParty(String newNumber){
        secondParty = newNumber;
    }

    public void OnRecv(String from, String contents){
        if(secondParty.equals(from)){
            SendToCopilot("SP->MP|" + contents);
            System.out.println("SP->MP|" + contents);
        }
        else if(coPilot.equals(from)){
            System.out.println(contents);
        }
        else{
            System.out.println("Who dis is?");
        }
    }

    public void SendToCopilot(String contents){
        SendToNumber(coPilot, "MP->CP|" + contents);
    }

    public void SendToSecondParty(String contents){
        SendToNumber(secondParty, contents);
        SendToCopilot("MP->SP|" + contents);
        System.out.println("Sent to SP and MP" + contents);
    }
}