package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

public class CoPilot extends Pilot{
    String mainPilot = "";

    CoPilotActivity activity;

    CoPilot(){
        super.manager = SmsManager.getDefault();}

    CoPilot(CoPilotActivity mainAc){
        super.manager = SmsManager.getDefault();
        activity = mainAc;
    }

    public void SetMainPilot(String newNumber){
        mainPilot = newNumber;
    }

    public void OnRecv(String from, String contents){
        System.out.println("OnRecv: " + from + " mp:" + mainPilot);
        if(mainPilot.equals("")){
            if(contents.substring(0,7).equals("MPRQST|")) {
                System.out.println("Somebody wants me");
                activity.handleRequest(from);
            }
        }
        else if(mainPilot.equals(from)){
            int pipe  = contents.indexOf("|");
            int arrow = contents.indexOf("->");

            if(pipe != 6 || arrow != 2 || contents.length() < 7){
                System.out.println("Whatchu tryin?");
            }
            else{
                String fromCode = contents.substring(0,2);
                String   toCode = contents.substring(4,6); //TODO: Watch these numbers?

                if(fromCode.equals("MP") && toCode.equals("SP")){
                    String payload = contents.substring(7);
                    System.out.println("MP to SP: " + payload);
                    activity.displayPassengerRightConversation(payload);
                }
                else if(fromCode.equals("SP") && toCode.equals("MP")){
                    String payload = contents.substring(7);
                    System.out.println("SP to MP: " + payload);
                    activity.displayPassengerLeftConversation(payload);
                }
                else if(fromCode.equals("MP") && toCode.equals("CP")){
                    String payload = contents.substring(7);
                    System.out.println("MP to CP: " + payload);
                    activity.displayGrpLeftConversation(payload);
                }
            }
        }
        else{
            System.out.println("Who dis is?");
        }
    }

    public void SendToMainPilot(String contents){
        SendToNumber(mainPilot, contents);
        activity.displayGrpRightConversation(contents);
    }
}
