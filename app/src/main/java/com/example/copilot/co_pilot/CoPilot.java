package com.example.copilot.co_pilot;

import android.telephony.SmsManager;

public class CoPilot {
    String mainPilot = "";

    SmsManager manager;
    MainActivity activity;

    //Some kind of reference to chatboxes

    CoPilot(){
        manager = SmsManager.getDefault();
    }

    CoPilot(MainActivity mainAc){
        manager = SmsManager.getDefault();
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

                if(fromCode == "MP" && toCode == "SP"){
                    System.out.println("MP->SP" + contents);
                }
                else if(fromCode == "SP" && toCode == "MP"){
                    System.out.println("SP->MP" + contents);
                }
                else if(fromCode == "MP" && toCode == "CP"){
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

    private void SendToNumber(String number, String content){
        manager.sendTextMessage(number, null, content, null, null);
    }


}
