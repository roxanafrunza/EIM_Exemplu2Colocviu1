package ro.pub.cs.systems.eim.practicaltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTestSecondaryActivitity extends AppCompatActivity {
    Button registerButton;
    Button cancelButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test_secondary_activitity);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonClickListener);
        textView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.COORDINATES)) {
            String coordinates = intent.getStringExtra(Constants.COORDINATES);
            textView.setText(String.valueOf(coordinates));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.registerButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

}
