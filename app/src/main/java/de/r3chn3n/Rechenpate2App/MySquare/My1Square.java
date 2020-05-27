package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My1Square extends MySquare {

    private final int ALPHA = 150;


    My1Square(float x, float y) {
        super(x, y);
        this.setNumOfSquaresX(1);
        this.setNumOfSquaresY(1);
        this.setElement(Element.Ones);
        this.setSquareHeight(2 * LENGTH);
        this.myPaint.setAlpha(ALPHA);
        this.myPaintBorder.setColor(BLUE_BORDER);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x + LENGTH &&
                event.getX() >= this.x - LENGTH &&
                event.getY() <= this.y + LENGTH  &&
                event.getY() >= this.y - LENGTH;
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return  getX() <= 0 ||
                getX() >= displayWidth - OFFSET ||
                getY() <= OFFSET ||
                getY() >= displayHeight - OFFSET;
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
