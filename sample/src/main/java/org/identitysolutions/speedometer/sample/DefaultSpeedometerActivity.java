package org.identitysolutions.speedometer.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.identitysolutions.speedometer.SpeedometerView;

public class DefaultSpeedometerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView mPercentageTextView;
    private TextView mDurationTextView;
    private SpeedometerView mSpeedometerView;
    private SeekBar mPercentageSeekBar;
    private SeekBar mDurationSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPercentageTextView = findViewById(R.id.txt_percentage);
        mDurationTextView = findViewById(R.id.txt_duration);
        mPercentageSeekBar = findViewById(R.id.seekBar_percentage);
        mDurationSeekBar = findViewById(R.id.seekBar_duration);

        mSpeedometerView = findViewById(R.id.speedometerView);

        findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateSpeedometer();
            }
        });

        mDurationSeekBar.setOnSeekBarChangeListener(this);
        mPercentageSeekBar.setOnSeekBarChangeListener(this);

        animateSpeedometer();
    }

    protected int getActivityLayout() {
        return R.layout.activity_default_speedometer;
    }

    private void animateSpeedometer() {

        mSpeedometerView.animatePercentage(mPercentageSeekBar.getProgress(), mDurationSeekBar.getProgress());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()) {
            case R.id.seekBar_duration:

                mDurationTextView.setText(String.valueOf(progress));
                break;
            case R.id.seekBar_percentage:

                mPercentageTextView.setText(getString(R.string.progress_percentage, progress));
                break;
            default:
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
