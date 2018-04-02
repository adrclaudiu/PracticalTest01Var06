package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class PracticalTest01Var02PlayActivity extends AppCompatActivity {
    private Button buttonGenerate, buttonCheck;
    private String numberReceived = new String();
    private EditText guessText, scoreText;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Random randomNumber = new Random();
            int scoreValue = 0;

            switch (view.getId()) {
                case R.id.generate:
                    int generatedNumber = randomNumber.nextInt(9 - 0 + 1);
                    guessText.setText(String.valueOf(generatedNumber));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            guessText.setText("");
                        }
                    }, 2000);
                    break;

                case R.id.check:
                    if (numberReceived.equals(guessText.getText().toString())) {
                        scoreValue++;
                        scoreText.setText(String.valueOf(scoreValue));
                    }
                    break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_play);

        buttonGenerate = (Button) findViewById(R.id.generate);
        buttonGenerate.setOnClickListener(buttonClickListener);
        buttonCheck = (Button) findViewById(R.id.check);
        buttonCheck.setOnClickListener(buttonClickListener);

        guessText = (EditText) findViewById(R.id.guessNumber);
        scoreText = (EditText) findViewById(R.id.score);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.NUMBER_SENT)) {
            this.numberReceived = intent.getStringExtra(Constants.NUMBER_SENT);
        }


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.GUESS)) {
                guessText.setText(savedInstanceState.getString(Constants.GUESS));
            } else {
                guessText.setText("");
            }
            if (savedInstanceState.containsKey(Constants.SCORE)) {
                scoreText.setText(savedInstanceState.getString(Constants.SCORE));
            } else {
                scoreText.setText("");
            }
        } else {
            guessText.setText("");
            scoreText.setText("");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.GUESS, guessText.getText().toString());
        savedInstanceState.putString(Constants.SCORE, scoreText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.GUESS)) {
            guessText.setText(savedInstanceState.getString(Constants.GUESS));
        } else {
            guessText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.SCORE)) {
            scoreText.setText(savedInstanceState.getString(Constants.SCORE));
        } else {
            scoreText.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }

}
