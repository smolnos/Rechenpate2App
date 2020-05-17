package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My1Square extends MySquare {


    My1Square(float x, float y) {
        super(x, y);
        this.setNumOfSquaresX(1);
        this.setNumOfSquaresY(1);
        this.setElement(Element.Ones);
        this.setSquareHeight(2 * LENGTH);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x + 2 * LENGTH &&
                event.getX() >= this.x - 2 * LENGTH &&
                event.getY() <= this.y + 2 *  LENGTH  &&
                event.getY() >= this.y - 2 * LENGTH;
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return  getX() <= 0 ||
                getX() >= displayWidth - OFFSET ||
                getY() <= OFFSET ||
                getY() >= displayHeight - OFFSET;
    }

}
