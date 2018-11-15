package org.identitysolutions.speedometer.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.layout_example_1).setOnClickListener(this);
        findViewById(R.id.layout_example_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_example_1:

                startActivity(new Intent(this, DefaultSpeedometerActivity.class));
                break;
            case R.id.layout_example_2:

                startActivity(new Intent(this, CustomSpeedometerActivity.class));
                break;
            default:
        }
    }
}
