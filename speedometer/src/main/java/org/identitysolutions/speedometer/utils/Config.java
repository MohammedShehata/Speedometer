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
    private int graphResourceId;
    private int arrowResourceId;
    private float pivotY;
    private float angelOfOnePercentage;
    private float arrowMarginTop;

    public Config(Context context, AttributeSet attrs) {

        this.mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SpeedometerView,
                0, 0);

        graphResourceId = typedArray.getResourceId(R.styleable.SpeedometerView_speedometerGraph, R.drawable.graph);
        arrowResourceId = typedArray.getResourceId(R.styleable.SpeedometerView_speedometerArrow, R.drawable.speedometer_arrow);
        pivotY = typedArray.getDimension(R.styleable.SpeedometerView_yPivot, getDimens(R.dimen.y_pivot));
        angelOfOnePercentage = typedArray.getFloat(R.styleable.SpeedometerView_angelOfOnePercent, getFloatValue(R.dimen.angel_of_one_percent));
        arrowMarginTop = typedArray.getDimension(R.styleable.SpeedometerView_arrowMarginTop, getDimens(R.dimen.arrow_margin_top));
    }

    private float getFloatValue(int resourceID) {
        TypedValue outValue = new TypedValue();
        mContext.getResources().getValue(resourceID, outValue, true);
        return outValue.getFloat();
    }

    private float getDimens(int dimenResourceId) {
        return mContext.getResources().getDimension(dimenResourceId);
    }

    public int getGraphResourceId() {
        return graphResourceId;
    }

    public int getArrowResourceId() {
        return arrowResourceId;
    }

    public float getPivotY() {
        return pivotY;
    }

    public float getAngelOfOnePercentage() {
        return angelOfOnePercentage;
    }

    public float getArrowMarginTop() {
        return arrowMarginTop;
    }
}
