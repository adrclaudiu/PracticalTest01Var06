package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class PracticalTest01Var03ChooseNumber extends AppCompatActivity {

    private EditText numberText;
    private Button playButton;
    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch(view.getId()) {
                case R.id.playButton:
                    if (numberText.getText().length() > 0) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02PlayActivity.class);
                        String numberValueSend = numberText.getText().toString();
                        intent.putExtra(Constants.NUMBER_SENT, numberValueSend);
                        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    }

                    Random rd = new Random();
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                    intent.putExtra(Constants.GUESS,rd.nextInt(9 - 0 + 1));
                    getApplicationContext().startService(intent);
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_choose_number);

        numberText = (EditText) findViewById(R.id.number);
        playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
