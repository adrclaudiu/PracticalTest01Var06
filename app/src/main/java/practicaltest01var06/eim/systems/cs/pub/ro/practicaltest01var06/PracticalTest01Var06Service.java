package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var06Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String firstNumber = intent.getStringExtra(Constants.GENERATED_NUMBER);
        processingThread = new ProcessingThread(this, firstNumber);
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
