package org.identitysolutions.speedometer.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.identitysolutions.speedometer.SpeedometerView;

public class DefaultSpeedometerActivity extends AppCompatActivity {

    private EditText mPercentageEditText;
    private EditText mDurationPerAngelEditText;
    private SpeedometerView mSpeedometerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        mPercentageEditText = findViewById(R.id.edit_text_percentage);
        mDurationPerAngelEditText = findViewById(R.id.edit_text_duration_per_angel);
        mSpeedometerView = findViewById(R.id.speedometerView);

        findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateSpeedometer();
            }
        });
    }

    protected int getActivityLayout() {
        return R.layout.activity_default_speedometer;
    }

    private void animateSpeedometer() {

        try {

            int percentage = Integer.valueOf(mPercentageEditText.getText().toString());
            int durationPerAngel = Integer.valueOf(mDurationPerAngelEditText.getText().toString());

            hideKeyboard();

            mSpeedometerView.animatePercentage(percentage, durationPerAngel);
        } catch (NumberFormatException e) {

            Toast.makeText(this, getString(R.string.invalid_input_numbers), Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

}
