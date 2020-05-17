package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My100Square extends MySquare {

    public final int ALPHA = 30;

    My100Square(float x, float y) {
        super(x, y);
        this.setNumOfSquaresX(10);
        this.setNumOfSquaresY(10);
        this.setElement(Element.Hundreds);
        this.setSquareHeight(2 * LENGTH * numOfSquaresY);
        this.setSquareWidth(2 * LENGTH * numOfSquaresX);
        this.myPaint.setAlpha(ALPHA);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x + LENGTH + LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getX() >= this.x - LENGTH - LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getY() <= this.y + LENGTH + LENGTH * 2 * (numOfSquaresY / 2)  &&
                event.getY() >= this.y - LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return getX() + LENGTH * (numOfSquaresX - 1)  <= 0 ||
                getX() - LENGTH - LENGTH * (numOfSquaresX - 4) >= displayWidth - OFFSET ||
                getY() + LENGTH * (numOfSquaresY - 4)  <= OFFSET ||
                getY() - LENGTH - LENGTH * (numOfSquaresY - 4)  >= displayHeight - OFFSET;
    }

    @Override
    public void setColor() {
        if (createElement) {
            setCreateElement(false);
        } else {
            if (this.myPaintBorder.getColor() == BLUE_BORDER) {
                this.myPaint.setColor(RED);
                this.myPaint.setAlpha(ALPHA);
                this.myPaintBorder.setColor(RED_BORDER);
            } else {
                this.myPaint.setColor(BLUE);
                this.myPaint.setAlpha(ALPHA);
                this.myPaintBorder.setColor(BLUE_BORDER);
            }
        }
    }
}
