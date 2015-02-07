package com.example.copilot.co_pilot;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //If since is null, then all messages are returned
    public SMSMessageDetails[] GetMessages(String number, Date since){
        Uri sentURI = Uri.parse("content://sms/inbox");
        String[] reqCols = new String[] { "_id", "address", "body", "date"};
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(sentURI, reqCols, null, null, null);

        if(c!=null && c.getCount()>0){
            c.moveToFirst();

            //SMSMessageDetails[] messages = new SMSMessageDetails[c.getCount()];
            ArrayList<SMSMessageDetails> messages = new ArrayList<SMSMessageDetails>();

            for(int i = 0; i < c.getCount(); i++) {
                String addr = c.getString(c.getColumnIndex("address"));
                String bod = c.getString(c.getColumnIndex("body"));
                Long dateLong = c.getLong(c.getColumnIndex("date"));
                Date time = new Date(dateLong);

                messages.add(new SMSMessageDetails(addr, time, bod));
                c.moveToNext();
            }

            c.close();
            SMSMessageDetails[] messagesArr = new SMSMessageDetails[messages.size()];
            messages.toArray(messagesArr);
            return messagesArr;
        }
        else{
            if(!c.isClosed()) {
                c.close();
            }
            return new SMSMessageDetails[0];
        }
    }
}
