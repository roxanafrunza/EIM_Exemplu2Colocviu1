package ro.pub.cs.systems.eim.practicaltest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private String coordinatesString;

    public ProcessingThread(Context context, String coordinatesString) {
        this.context = context;

        this.coordinatesString = coordinatesString;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        sleep();
        sendMessage();
        while(isRunning){}
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionType);
        intent.putExtra(Constants.COORDINATES_STRING, new Date(System.currentTimeMillis()) + ": " + coordinatesString);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
