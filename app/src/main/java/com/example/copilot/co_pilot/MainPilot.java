package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

/**
 * Created by Benji Smith on 2/7/2015.
 */

public class MainPilot extends Pilot{

    String secondParty = "";
    String coPilot = "";
    String potentialCoPilot = "";

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
        System.out.println("!!!!!" + potentialCoPilot + "  from: " + from);
        if(coPilot.equals("")) {
            if(from.equals(potentialCoPilot) || from.equals("+1" + potentialCoPilot)) {
                System.out.println("This is what you want " + contents.substring(0,7));
                if(contents.substring(0,7).equals("CPRQYS|")) {
                    System.out.println("He said Yes!" + potentialCoPilot + " " + coPilot + "!");
                    coPilot = potentialCoPilot;
                    potentialCoPilot = "";
                }
                else if(contents.substring(0,7).equals("CPRQNO|")) {
                    System.out.println("He said.... NOo!" + potentialCoPilot + " " + coPilot + "!");
                    potentialCoPilot = "";
                }
            }
        }
        else if(from.equals("+1" + secondParty) || from.equals(secondParty)){
            SendToNumber(coPilot, "SP->MP|" + contents);
            System.out.println("SP->MP|" + contents);
            activity.displayPassengerLeftConversation(contents);
            activity.displayPassengerRightConversation("\n");
        }
        else if(from.equals("+1" + coPilot) || from.equals(coPilot)){
            System.out.println(contents);
            activity.displayGrpLeftConversation(contents);
            activity.displayGrpRightConversation("\n");
        }
        else{
            System.out.println("Who dis is?" + coPilot + ": " + from);
        }
    }

    public void sendRequest(String number) {
        potentialCoPilot = number;
        SendToNumber(number, "MPRQST|");
    }

    public void SendToCopilot(String contents){
        SendToNumber(coPilot, "MP->CP|" + contents);
        activity.displayGrpRightConversation(contents);
        activity.displayGrpLeftConversation("\n");
    }

    public void SendToSecondParty(String contents){
        SendToNumber(secondParty, contents);
        SendToNumber(coPilot, "MP->SP|" + contents);
        activity.displayPassengerRightConversation(contents);
        activity.displayPassengerLeftConversation("\n");
        System.out.println("Sent to SP and MP" + contents);
    }
}
