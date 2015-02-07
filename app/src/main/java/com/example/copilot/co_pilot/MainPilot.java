package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by Benji Snith on 2/7/2015.
 */
public class MainPilot {

    String secondParty = "";
    String coPilot = "";

    SmsManager manager;

    MainActivity activity;

    //Some kind of reference to chatboxes

    MainPilot(){
        manager = SmsManager.getDefault();
    }

    MainPilot(MainActivity mainAc){
        manager = SmsManager.getDefault();
        activity = mainAc;
    }

    public void SetCopilot(String newNumber){
        coPilot = newNumber;
    }

    public void OnRecv(String from, String contents){
        if(secondParty.equals(from)){
            //Display in the main chat
            SendToCopilot("SP->MP|" + contents);
        }
        else if(coPilot.equals(from)){
            //Display in group chat
        }
        else{
            //Who dis is?
        }
    }

    public void SendToCopilot(String contents){
        SendToNumber(coPilot, "MP->CP|" + contents);
    }

    public void SendToSecondParty(String contents){
        SendToNumber(secondParty, contents);
        SendToCopilot("MP->SP|" + contents);
    }

    private void SendToNumber(String number, String content){
        manager.sendTextMessage(number, null, content, null, null);
    }


}
