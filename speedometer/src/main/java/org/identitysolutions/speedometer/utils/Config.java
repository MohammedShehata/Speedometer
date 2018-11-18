package org.identitysolutions.speedometer.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.identitysolutions.speedometer.R;

/**
 * Created by shehata on 11/13/18.
 */

public class Config {


    private Context mContext;
    private int gaugeResourceId;
    private int needleResourceId;
    private float needlePivotY;
    private float angelOfOnePercentage;
    private float needleMarginTop;

    public Config(Context context, AttributeSet attrs) {

        this.mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SpeedometerView,
                0, 0);

        gaugeResourceId = typedArray.getResourceId(R.styleable.SpeedometerView_speedometerGauge, R.drawable.speedometer_gauge);
        needleResourceId = typedArray.getResourceId(R.styleable.SpeedometerView_speedometerNeedle, R.drawable.speedometer_needle);
        needlePivotY = typedArray.getDimension(R.styleable.SpeedometerView_needleYPivot, getDimens(R.dimen.needle_y_pivot));
        angelOfOnePercentage = typedArray.getFloat(R.styleable.SpeedometerView_angelOfOnePercent, getFloatValue(R.dimen.angel_of_one_percent));
        needleMarginTop = typedArray.getDimension(R.styleable.SpeedometerView_needleMarginTop, getDimens(R.dimen.needle_margin_top));
    }

    private float getFloatValue(int resourceID) {
        TypedValue outValue = new TypedValue();
        mContext.getResources().getValue(resourceID, outValue, true);
        return outValue.getFloat();
    }

    private float getDimens(int dimenResourceId) {
        return mContext.getResources().getDimension(dimenResourceId);
    }

    public int getGaugeResourceId() {
        return gaugeResourceId;
    }

    public int getNeedleResourceId() {
        return needleResourceId;
    }

    public float getNeedlePivotY() {
        return needlePivotY;
    }

    public float getAngelOfOnePercentage() {
        return angelOfOnePercentage;
    }

    public float getNeedleMarginTop() {
        return needleMarginTop;
    }
}
