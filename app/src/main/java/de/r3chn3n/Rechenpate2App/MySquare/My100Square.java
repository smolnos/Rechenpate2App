package de.r3chn3n.Rechenpate2App.MySquare;

import android.view.MotionEvent;

public class My100Square extends MySquare {

    public My100Square(float x, float y) {
        super(x, y);
        this.setNumOfSquaresX(10);
        this.setNumOfSquaresY(10);
        this.setElement(Element.Hundreds);
        this.setSquareHeight(2 * LENGTH * numOfSquaresY);
        this.setSquareWidth(2 * LENGTH * numOfSquaresX);
    }

    @Override
    public boolean isEventInSquare(MotionEvent event) {
        return  event.getX() <= this.x + LENGTH + LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getX() >= this.x - LENGTH - LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getY() <= this.y + LENGTH + LENGTH * 2 * (numOfSquaresY / 2)  &&
                event.getY() >= this.y - LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean shouldColorBeChanged(MotionEvent event, float oldEventX, float oldEventY) {
        return  event.getX() - oldEventX <= getSquareWidth() + LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getX() - oldEventX >= -LENGTH - LENGTH * 2 * (numOfSquaresX / 2) &&
                event.getY() - oldEventY <= getSquareHeight() + LENGTH * 2 * (numOfSquaresY / 2) &&
                event.getY() - oldEventY >= -LENGTH - LENGTH * 2 * (numOfSquaresY / 2);
    }

    @Override
    public boolean outOfScreen(float displayWidth, float displayHeight) {
        return getX() + LENGTH * 2 * (numOfSquaresX / 2)  <= 0 ||
                getX() - LENGTH - LENGTH * 2 * (numOfSquaresX / 2) >= displayWidth - OFFSET ||
                getY() + LENGTH * 2 * (numOfSquaresY / 2)  <= OFFSET ||
                getY() - LENGTH - LENGTH * 2 * (numOfSquaresY / 2)  >= displayHeight - OFFSET;
    }
}
