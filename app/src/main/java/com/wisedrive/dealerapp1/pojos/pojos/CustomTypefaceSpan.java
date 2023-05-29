package com.wisedrive.dealerapp1.pojos.pojos;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomTypefaceSpan extends MetricAffectingSpan {
    private Typeface typeface;
  //  private String fontFamily;

    public CustomTypefaceSpan( Typeface typeface) {
       // this.fontFamily = fontFamily;
        this.typeface = typeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeface(ds);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeface(paint);
    }

    private void applyCustomTypeface(Paint paint) {
        Typeface oldTypeface = paint.getTypeface();
        int oldStyle = oldTypeface != null ? oldTypeface.getStyle() : 0;
        int fakeStyle = oldStyle & ~typeface.getStyle();

     /*   if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        } */

        paint.setTypeface(typeface);
    }
}
