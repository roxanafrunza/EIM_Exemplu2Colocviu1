package ro.pub.cs.systems.eim.practicaltest;

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
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTestMainActivity extends AppCompatActivity {
    Button northButton;
    Button eastButton;
    Button westButton;
    Button southButton;
    Button navigateButton;
    TextView textView;

    private IntentFilter intentFilter = new IntentFilter();
    public int numberOfClicks = 0;
    public int serviceStatus = Constants.SERVICE_STOPPED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test_main);

        northButton = (Button) findViewById(R.id.northButton);
        northButton.setOnClickListener(coordButtonListener);

        westButton = (Button) findViewById(R.id.westButton);
        westButton.setOnClickListener(coordButtonListener);

        eastButton = (Button) findViewById(R.id.eastButton);
        eastButton.setOnClickListener(coordButtonListener);

        southButton = (Button) findViewById(R.id.southButton);
        southButton.setOnClickListener(coordButtonListener);

        textView = (TextView) findViewById(R.id.coordinatesTextView);
        navigateButton = (Button) findViewById(R.id.navigateButton);
        navigateButton.setOnClickListener(navigateButtonListener);



       intentFilter.addAction(Constants.actionType);

    }

    CoordinateButtonListener coordButtonListener = new CoordinateButtonListener();
    private class CoordinateButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String text = textView.getText().toString();

            switch(v.getId()) {
                case(R.id.northButton):
                    textView.setText(text + "North, ");
                    numberOfClicks++;
                    break;
                case(R.id.westButton):
                    textView.setText(text + "West, ");
                    numberOfClicks++;
                    break;
                case(R.id.eastButton):
                    textView.setText(text + "East, ");
                    numberOfClicks++;
                    break;
                case(R.id.southButton):
                    textView.setText(text + "South, ");
                    numberOfClicks++;
                    break;
            }

            if (numberOfClicks > 4
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTestService.class);
                intent.putExtra(Constants.COORDINATES_STRING, textView.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.NUMBER_OF_CLICKS, numberOfClicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.NUMBER_OF_CLICKS)) {
           numberOfClicks = savedInstanceState.getInt(Constants.NUMBER_OF_CLICKS);
        } else {
           numberOfClicks = 0;
        }
    }
    NavigateButtonListener navigateButtonListener = new NavigateButtonListener();
    private class NavigateButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PracticalTestSecondaryActivitity.class);
            String coordinates = textView.getText().toString();
            intent.putExtra(Constants.COORDINATES, coordinates);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK)
                Toast.makeText(this, "The register button was pressed", Toast.LENGTH_LONG).show();
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(this, "The cancel button was pressed", Toast.LENGTH_LONG).show();
            textView.setText("");
            numberOfClicks = 0;
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTestService.class);
        stopService(intent);
        super.onDestroy();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra(Constants.COORDINATES_STRING));
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
}
