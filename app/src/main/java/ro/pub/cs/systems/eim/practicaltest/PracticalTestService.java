package ro.pub.cs.systems.eim.practicaltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTestService extends Service {
    ProcessingThread processingThread = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String coordinates = intent.getStringExtra(Constants.COORDINATES_STRING);
        processingThread = new ProcessingThread(this, coordinates);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
