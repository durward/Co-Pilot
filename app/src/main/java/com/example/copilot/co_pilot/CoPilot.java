package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

public class CoPilot extends Pilot{
    String mainPilot = "";

    MainActivity activity;

    CoPilot(){
        super.manager = SmsManager.getDefault();}

    CoPilot(MainActivity mainAc){
        super.manager = SmsManager.getDefault();
        activity = mainAc;
    }

    public void SetMainPilot(String newNumber){
        mainPilot = newNumber;
    }

    public void OnRecv(String from, String contents){
        if(mainPilot.equals(from)){
            int pipe  = contents.indexOf("|");
            int arrow = contents.indexOf("->");

            if(pipe != 6 || arrow != 2 || contents.length() < 7){
                System.out.println("Whatchu tryin?");
            }
            else{
                String fromCode = contents.substring(0,2);
                String   toCode = contents.substring(4,6); //TODO: Watch these numbers?

                if(fromCode.equals("MP") && toCode.equals("SP")){
                    System.out.println("MP->SP" + contents);
                }
                else if(fromCode.equals("SP") && toCode.equals("MP")){
                    System.out.println("SP->MP" + contents);
                }
                else if(fromCode.equals("MP") && toCode.equals("CP")){
                    System.out.println("MP->SP" + contents);
                }
            }
        }
        else{
            System.out.println("Who dis is?");
        }
    }

    public void SendToMainPilot(String contents){
        SendToNumber(mainPilot, contents);
    }
}
