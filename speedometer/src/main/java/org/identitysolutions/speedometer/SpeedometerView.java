package org.identitysolutions.speedometer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.identitysolutions.speedometer.utils.Config;

/**
 * Created by shehata on 7/17/18.
 */

public class SpeedometerView extends RelativeLayout {

    private static final String TAG = SpeedometerView.class.getSimpleName();
    private static final int DEFAULT_DURATION_PER_ONE_PERCENTAGE = 25;

    private View mSpeedometerGaugeLayout;
    private ImageView mSpeedometerNeedleImageView;
    private volatile boolean canAnimate;
    private AnimateSpeedometerTask animateSpeedometerTask;
    private float pivotXValue;
    private float pivotYValue;
    private Config config;

    public SpeedometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViews();
        initAttrs(attrs);

    }

    private void initViews() {

        inflate(getContext(), R.layout.view_speedometer, this);

        canAnimate = false;

        mSpeedometerGaugeLayout = findViewById(R.id.layout_speedometer_gauge);
        mSpeedometerNeedleImageView = findViewById(R.id.img_speedometer_needle);

        mSpeedometerNeedleImageView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, mSpeedometerNeedleImageView.getHeight() + " - " + mSpeedometerNeedleImageView.getWidth());
                setCanAnimate(true);

                pivotXValue = mSpeedometerNeedleImageView.getWidth() / 2.0f;
                pivotYValue = mSpeedometerNeedleImageView.getHeight() - config.getNeedlePivotY() * 1f;
                initSpeedometerArrowPosition();

                if (animateSpeedometerTask != null) {

                    animateSpeedometerTask.onSpeedometerCreated();
                }
            }
        });
    }

    private void initAttrs(AttributeSet attrs) {

        config = new Config(getContext(), attrs);

        mSpeedometerGaugeLayout.setBackgroundResource(config.getGaugeResourceId());
        mSpeedometerNeedleImageView.setImageResource(config.getNeedleResourceId());

        LayoutParams layoutParams = (LayoutParams) mSpeedometerNeedleImageView.getLayoutParams();
        layoutParams.setMargins(0, (int) config.getNeedleMarginTop(), 0, 0);
        mSpeedometerNeedleImageView.setLayoutParams(layoutParams);
    }

    private void initSpeedometerArrowPosition() {

        mSpeedometerNeedleImageView.setPivotX(pivotXValue);
        mSpeedometerNeedleImageView.setPivotY(pivotYValue);
        mSpeedometerNeedleImageView.setRotation(-50f * config.getAngelOfOnePercentage());
    }

    private void rotateSpeedometerArrow(float percentage, long duration) {

        RotateAnimation rotateAnimation = new RotateAnimation(0, percentage * config.getAngelOfOnePercentage(), Animation.ABSOLUTE,
                pivotXValue, Animation.ABSOLUTE, pivotYValue);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        mSpeedometerNeedleImageView.startAnimation(rotateAnimation);
    }

    /**
     * @param percentage to be represented from 0 to 100
     * @param duration   the time to animate rotation in milliseconds
     */

    public synchronized void animatePercentage(final int percentage, final int duration) {

        if (canAnimate()) {

            initSpeedometerArrowPosition();
            rotateSpeedometerArrow(percentage, duration);
        } else {

            animateSpeedometerTask = new AnimateSpeedometerTask() {
                @Override
                public void onSpeedometerCreated() {

                    rotateSpeedometerArrow(percentage, duration);
                }
            };
        }
    }

    /**
     * animate by default duration per one percentage * percentage
     *
     * @param percentage to be represented from 0 to 100
     */
    public synchronized void animatePercentage(int percentage) {

        animatePercentage(percentage, percentage * DEFAULT_DURATION_PER_ONE_PERCENTAGE);
    }

    private synchronized void setCanAnimate(boolean canAnimate) {
        this.canAnimate = canAnimate;
    }

    public synchronized boolean canAnimate() {
        return canAnimate;
    }

    interface AnimateSpeedometerTask {

        void onSpeedometerCreated();
    }
}
