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

    private View mSpeedometerGraphLayout;
    private ImageView mSpeedometerArrowImageView;
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

        mSpeedometerGraphLayout = findViewById(R.id.layout_speedometer_graph);
        mSpeedometerArrowImageView = findViewById(R.id.img_speedometer_arrow);

        mSpeedometerArrowImageView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, mSpeedometerArrowImageView.getHeight() + " - " + mSpeedometerArrowImageView.getWidth());
                setCanAnimate(true);

                pivotXValue = mSpeedometerArrowImageView.getWidth() / 2.0f;
                pivotYValue = mSpeedometerArrowImageView.getHeight() - config.getPivotY() * 1f;
                initSpeedometerArrowPosition();

                if (animateSpeedometerTask != null) {

                    animateSpeedometerTask.onSpeedometerCreated();
                }
            }
        });
    }

    private void initAttrs(AttributeSet attrs) {

        config = new Config(getContext(), attrs);

        mSpeedometerGraphLayout.setBackgroundResource(config.getGraphResourceId());
        mSpeedometerArrowImageView.setImageResource(config.getArrowResourceId());

        LayoutParams layoutParams = (LayoutParams) mSpeedometerArrowImageView.getLayoutParams();
        layoutParams.setMargins(0, (int) config.getArrowMarginTop(), 0, 0);
        mSpeedometerArrowImageView.setLayoutParams(layoutParams);
    }

    private void initSpeedometerArrowPosition() {

        mSpeedometerArrowImageView.setPivotX(pivotXValue);
        mSpeedometerArrowImageView.setPivotY(pivotYValue);
        mSpeedometerArrowImageView.setRotation(-50f * config.getAngelOfOnePercentage());
    }

    private void rotateSpeedometerArrow(float percentage, long duration) {

        RotateAnimation rotateAnimation = new RotateAnimation(0, percentage * config.getAngelOfOnePercentage(), Animation.ABSOLUTE,
                pivotXValue, Animation.ABSOLUTE, pivotYValue);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        mSpeedometerArrowImageView.startAnimation(rotateAnimation);
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
