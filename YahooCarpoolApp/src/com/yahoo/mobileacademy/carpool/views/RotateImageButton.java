package com.yahoo.mobileacademy.carpool.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Custom view that Rotate an ImageButton by 180 degrees
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class RotateImageButton extends ImageButton{

    public RotateImageButton(Context context) {
        super(context);
    }

    public RotateImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(180, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
        canvas.restore();
    }

}
