package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My10Square extends MySquare {

    My10Square(float x, float y) {
        super(x,  y);
        this.setNumOfSquaresX(1);
        this.setNumOfSquaresY(10);
        this.setElement(Element.Tens);
        this.setSquareHeight(2 * LENGTH * numOfSquaresY);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x +  2 * LENGTH &&
                event.getX() >= this.x -  2 * LENGTH &&
                event.getY() <= this.y + LENGTH + LENGTH * 2 * (numOfSquaresY / 2)  &&
                event.getY() >= this.y - LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return  getX() <= 0 ||
                getX() >= displayWidth - OFFSET ||
                getY() + LENGTH * (numOfSquaresY - 4)  <= OFFSET ||
                getY() - LENGTH - LENGTH * (numOfSquaresY - 4)  >= displayHeight - OFFSET;
    }
}
