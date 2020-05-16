package de.r3chn3n.Rechenpate2App;

import android.graphics.Color;
import android.graphics.Paint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySquare {
    public static final float LENGTH = 20;
    public static final float LENGTH_BORDER = 20;
    public static final int BLUE_BORDER = Color.parseColor("#000ffa");
    public static final int BLUE = Color.parseColor("#4287f5");
    public static final int RED_BORDER = Color.parseColor("#ff0042");
    public static final int RED = Color.parseColor("#ff9bb5");

    private float x;
    private float y;
    private Paint myPaint;
    private Paint myPaintBorder;
    private String color;
    private Element element;
    private float squareWidth;
    private float squareHeight;

    MySquare() {
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(BLUE);
        myPaint.setAlpha(75);
        myPaint.setAntiAlias(true);
        myPaintBorder = new Paint();
        myPaintBorder.setStyle(Paint.Style.STROKE);
        myPaintBorder.setStrokeWidth(6F);
        myPaintBorder.setColor(BLUE_BORDER);
        myPaintBorder.setAntiAlias(true);
        element = Element.Ones;
        squareWidth = LENGTH;
        squareHeight = LENGTH;
    }

    void setColor() {
        if (this.myPaintBorder.getColor() == BLUE_BORDER) {
            this.myPaint.setColor(RED);
            this.myPaint.setAlpha(75);
            this.myPaintBorder.setColor(RED_BORDER);
        } else {
            this.myPaint.setColor(BLUE);
            this.myPaint.setAlpha(75);
            this.myPaintBorder.setColor(BLUE_BORDER);
        }
    }

    public void setElementByHeight(float heightDisplay) {
        if (this.y < 0.33 * heightDisplay) {
            this.setElement(Element.Hundreds);
            this.setSquareHeight(LENGTH * 10);
            this.setSquareWidth(LENGTH * 10);
        } else if (this.y < 0.66 * heightDisplay){
            this.setElement(Element.Tens);
            this.setSquareHeight(LENGTH * 10);
        }
    }

    public void setElementByWidth(float widthDisplay) {
        if (this.x < 0.33 * widthDisplay) {
            this.setElement(Element.Hundreds);
            this.setSquareHeight(LENGTH * 10);
            this.setSquareWidth(LENGTH * 10);
        } else if (this.x < 0.66 * widthDisplay){
            this.setElement(Element.Tens);
            this.setSquareHeight(LENGTH * 10);
        }
    }
}
