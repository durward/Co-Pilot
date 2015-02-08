package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by Benji Snith on 2/7/2015.
 */

public class MainPilot extends Pilot{

    String secondParty = "";
    String coPilot = "";

 IActivity activity;

    MainPilot(){
        super.manager = SmsManager.getDefault();
    }

    MainPilot(IActivity mainAc){
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
            SendToNumber(coPilot, "SP->MP|" + contents);
            System.out.println("SP->MP|" + contents);
            activity.displayPassengerConversation(contents);
        }
        else if(coPilot.equals(from)){
            System.out.println(contents);
            activity.displayGrpConversation(contents);
        }
        else{
            System.out.println("Who dis is?" + coPilot + ": " + from);
        }
    }

    public void SendToCopilot(String contents){
        SendToNumber(coPilot, "MP->CP|" + contents);
        activity.displayGrpConversation(contents);
    }

    public void SendToSecondParty(String contents){
        SendToNumber(secondParty, contents);
        SendToNumber(coPilot, "MP->SP|" + contents);
        activity.displayPassengerConversation(contents);
        System.out.println("Sent to SP and MP" + contents);
    }
}
