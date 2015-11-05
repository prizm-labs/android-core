package com.prizm.jonah.unitycommunicationhelloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class HelloService extends Service {
    /*
    Tutorials Point Generic Service Variables
     */
    int mStartMode;
    IBinder mBinder;
    boolean mAllowRebind;

    Handler mHandler;


    /*
    Jean Meyblum Unity Service communication variables
     */
    private final Handler handler = new Handler();
    private int numIntent;

    //code for handler to execute
    //sends data to Unity
    private Runnable sendData = new Runnable() {
        //specific method that's executed by handler
        public void run() {
            numIntent++;
            Message temp_msg = new Message();
            temp_msg.obj = "Num intent: " + String.valueOf(numIntent);
            mHandler.sendMessage(temp_msg);


            //sendIntent is broadcasted outside our app
            Intent sendIntent = new Intent();

            //add flags to work from background
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_FROM_BACKGROUND|Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

            //setAction identifies the sender of the intent
            //by convention it's current package name
            //sendIntent.setAction("com.prizm.jonah.unitycommunicationhelloworld");
            sendIntent.setAction("com.test.sendintent.IntentToUnity");

            sendIntent.putExtra(Intent.EXTRA_TEXT, "Intent " +numIntent);

            //our message is sent to any other app that wants to listen:
            sendBroadcast(sendIntent);

            //run method each second with postDelayed
            handler.removeCallbacks(this);
            handler.postDelayed(this, 1000);

        }
    };

    //When service is started
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        numIntent = 0;
        Log.v("HelloService", "service started");
        Toast.makeText(this, "Hello from Service", Toast.LENGTH_LONG).show();

        handler.removeCallbacks(sendData);
        handler.postDelayed(sendData, 1000);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String mString = (String)msg.obj;
                Toast.makeText(HelloService.this, mString, Toast.LENGTH_LONG).show();
                Log.v("Num Intent: ", mString);
            }
        };

        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Goodbye from Service", Toast.LENGTH_LONG).show();
    }

    /*

    Unnecessary extra example service functions
     */
    /*
    @Override
    public void onCreate() {


    }


    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {


    }

    @Override
    public void onDestroy() {
        Log.v("HelloService", "Service destroyed");
    }

    */
}