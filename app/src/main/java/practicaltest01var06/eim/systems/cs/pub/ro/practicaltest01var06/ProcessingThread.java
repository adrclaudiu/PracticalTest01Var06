package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Random;

/**
 * Created by claudiu on 4/2/18.
 */

public class ProcessingThread extends Thread{
    private Context context = null;
    private boolean isRunning = true;
    private int genNumber;
    private Random random = new Random();


    public ProcessingThread(Context context, String firstNumber) {
        this.context = context;
        Random rd = new Random();
        this.genNumber = rd.nextInt(9 - 0 + 1);
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Random rd = new Random();
        this.genNumber = rd.nextInt(9 - 0 + 1);

        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                genNumber);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
