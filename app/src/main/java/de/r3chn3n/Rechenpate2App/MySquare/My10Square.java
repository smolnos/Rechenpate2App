package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My10Square extends MySquare {

    public My10Square(float x, float y) {
        super(x,  y);
        this.setNumOfSquaresX(1);
        this.setNumOfSquaresY(10);
        this.setElement(Element.Tens);
        this.setSquareHeight(2 * LENGTH * numOfSquaresY);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x + LENGTH &&
                event.getX() >= this.x - LENGTH &&
                event.getY() <= this.y + LENGTH + LENGTH * 2 * (numOfSquaresY / 2)  &&
                event.getY() >= this.y - LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean shouldColorBeChanged(MotionEvent event, float oldEventX, float oldEventY) {
        return  event.getX() - oldEventX <= getSquareWidth() &&
                event.getX() - oldEventX >= -LENGTH &&
                event.getY() - oldEventY <= getSquareHeight() + LENGTH * 2 * (numOfSquaresY / 2) &&
                event.getY() - oldEventY >= -LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return  getX() <= 0 ||
                getX() >= displayWidth - OFFSET ||
                getY() + LENGTH * 2 * (numOfSquaresY / 2)  <= OFFSET ||
                getY() - LENGTH - LENGTH * 2 * (numOfSquaresY / 2)  >= displayHeight - OFFSET;
    }
}
