package de.r3chn3n.Rechenpate2App;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaintView extends View {

    private final int OFFSET = 30;
    private float length;
    private float msNewPositionX = 30;
    private float msNewPositionY = 30;
    private float msOnTouchX;
    private float msOnTouchY;
    private float moveX;
    private float moveY;
    int indexMySquares = 0;
    boolean changed = false;
    private List<MySquare> mySquares = new ArrayList<>();
    private boolean createElement;


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintView(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isSquareNotPresent(event)) {
                    addNewSquare(event);
                    createElement = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (squareOutOfScreen()) {
                    deleteSquare();
                } else {
                    if (createElement) {
                        createElement = false;
                    } else {
                        changeColor(event);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveSquare(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * check if current event touched a rectangle
     * @param mySquare instance of class MySquare taken from list mySquares
     * @param event instance of class Motionevent
     * @return true if event with point (eventX, eventY) is within rectangle having center
     * (mySquareX, mySquareY) and length LENGTH
     */
    private boolean isEventInSquare(MySquare mySquare, MotionEvent event) {
        return event.getX() <= mySquare.getX() + 2 * mySquare.getSquareWidth()  + OFFSET / 2. && event.getX() >= mySquare.getX()
                -  OFFSET / 2. && event.getY() <= mySquare.getY()  + 2 * mySquare.getSquareHeight() + OFFSET / 2. &&
                event.getY() >= mySquare.getY() -  OFFSET / 2.;
    }

    /**
     * check if new touch is whithin a rectangle. If so, set indexMySquares to this rectangle
     * and return that that rectangle is already present, i.e. false. If no, just return true
     * that that rectangle is not present
     * @return boolean true if rectangle is not in list mySquares
     */
    private boolean isSquareNotPresent(MotionEvent event) {
        moveX = event.getX();
        moveY = event.getY();

        boolean circleNotPresent = true;
        int i = 0;
        for (MySquare ms : mySquares) {
            if (isEventInSquare(ms, event)) {
                circleNotPresent = false;
                msOnTouchX = ms.getX();
                msOnTouchY =  ms.getY();
                indexMySquares = i; //index of myCircles of that instance myCircle which should be
                // changed later (position, color, deletion)
                break;
            }
            i++;
        }
        return circleNotPresent;
    }

    /**
     * create a new instance of the class MyCircle and add this with new paramter of the event to
     * the list myCircles. Set myIdCircle on the position of this new instantiated object
     */
    private void addNewSquare(MotionEvent event) {
        MySquare newSquare = new MySquare();
        newSquare.setX(moveX);
        newSquare.setY(moveY);
        if (getWidth() > getHeight()) {
            newSquare.setElementByWidth(getWidth());
        } else {
            newSquare.setElementByHeight(getHeight());
        }
        mySquares.add(newSquare);
        msOnTouchX = moveX;
        msOnTouchY = moveY;
        indexMySquares = mySquares.size() - 1;
        msNewPositionX = msOnTouchX + event.getX() - moveX;
        msNewPositionY = msOnTouchY + event.getY() - moveY;
    }

    /**
     * change color of this circle if the event just touched this circle and does not move in
     * another direction
     * @param event parameter of the new event needed to compute new position of this circle
     */
    private void changeColor(MotionEvent event) {
        msNewPositionX = msOnTouchX + event.getX() - moveX;
        msNewPositionY = msOnTouchY + event.getY() - moveY;
        if (msNewPositionX <= msOnTouchX +  2 * mySquares.get(indexMySquares).getSquareWidth() * 2/3  && msNewPositionX >= msOnTouchX -  MySquare.LENGTH
                && msNewPositionY <= msOnTouchY + 2 * mySquares.get(indexMySquares).getSquareHeight() * 2/3  && msNewPositionY >= msOnTouchY -    MySquare.LENGTH ) {
            mySquares.get(indexMySquares).setColor();
        }
    }

    /**
     * set new position due to the new event to this circle
     * @param event paramer of the new event needed to compute new position of this circle
     */
    private void moveSquare(MotionEvent event) {
        msNewPositionX = msOnTouchX + event.getX() - moveX;
        msNewPositionY = msOnTouchY + event.getY() - moveY;
        mySquares.get(indexMySquares).setX(msNewPositionX);
        mySquares.get(indexMySquares).setY(msNewPositionY);
    }

    /**
     * check if the given point is out of the canvas
     * @return true if x value or y value are below offset or greater than width/ height
     */
    private boolean squareOutOfScreen() {
        return msNewPositionX >= getWidth() - OFFSET
                || msNewPositionY <= OFFSET || msNewPositionY >= getHeight() - OFFSET;
    }

    /**
     * delete circles if center is outside the screen
     */
    private void deleteSquare() {
        if (mySquares.isEmpty()) return;
        mySquares.remove(indexMySquares);
        if (mySquares.isEmpty()) return;
        msNewPositionX = mySquares.get(mySquares.size() - 1).getX();
        msNewPositionY = mySquares.get(mySquares.size() - 1).getY();
    }

    /**
     * second draw all rectangles twice with different radius to simulate border of rectangle
     * @param canvas canvas on which the rectangle are drawn
     */
    public void draw(Canvas canvas) {

        super.draw(canvas);
        for (MySquare mySquare : mySquares) {
            switch (mySquare.getElement()) {
                case Ones:
                    canvas.drawRect(mySquare.getX() - mySquare.LENGTH_BORDER, mySquare.getY() - mySquare.LENGTH_BORDER,
                            mySquare.getX() + mySquare.LENGTH_BORDER, mySquare.getY() + mySquare.LENGTH_BORDER,
                            mySquare.getMyPaint());
                    canvas.drawRect(mySquare.getX() - mySquare.LENGTH, mySquare.getY() - mySquare.LENGTH,
                        mySquare.getX() + mySquare.LENGTH, mySquare.getY() + mySquare.LENGTH,
                        mySquare.getMyPaintBorder());
                    break;
                case Tens:
                    for (int i = 0; i < 10; i++) {
                        canvas.drawRect(mySquare.getX() - mySquare.LENGTH_BORDER, mySquare.getY() - mySquare.LENGTH_BORDER + (2 * i) * mySquare.LENGTH_BORDER + (i - 1) * 3 + 6,
                                mySquare.getX() + mySquare.LENGTH_BORDER, mySquare.getY() + mySquare.LENGTH_BORDER + (2 * i) * mySquare.LENGTH_BORDER  + (i - 1) * 3 + 6,
                                mySquare.getMyPaint());
                        canvas.drawRect(mySquare.getX() - mySquare.LENGTH, mySquare.getY() + (2 * i - 1) * mySquare.LENGTH_BORDER  + (i - 1) * 3 + 6,
                                mySquare.getX() + mySquare.LENGTH, mySquare.getY() + (2 * i ) *  mySquare.LENGTH_BORDER + mySquare.LENGTH  + (i - 1) * 3 + 6,
                                mySquare.getMyPaintBorder());
                    }
                    break;
                case Hundreds:
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            canvas.drawRect(mySquare.getX() - mySquare.LENGTH_BORDER + (2 * j) * mySquare.LENGTH_BORDER + (j - 1) * 3 + 6, mySquare.getY() - mySquare.LENGTH_BORDER + (2 * i) * mySquare.LENGTH_BORDER + (i - 1) * 3 + 6,
                                    mySquare.getX() + mySquare.LENGTH_BORDER + (2 * j) * mySquare.LENGTH_BORDER + (j - 1) * 3 + 6, mySquare.getY() + mySquare.LENGTH_BORDER + (2 * i) * mySquare.LENGTH_BORDER + (i - 1) * 3 + 6,
                                    mySquare.getMyPaint());
                            canvas.drawRect(mySquare.getX() - mySquare.LENGTH + (2 * j) * mySquare.LENGTH_BORDER + (j - 1) * 3 + 6, mySquare.getY() + (2 * i - 1) * mySquare.LENGTH_BORDER + (i - 1) * 3 + 6,
                                    mySquare.getX() + mySquare.LENGTH + (2 * j) * mySquare.LENGTH_BORDER + (j - 1) * 3 + 6, mySquare.getY() + (2 * i) * mySquare.LENGTH_BORDER + mySquare.LENGTH + (i - 1) * 3 + 6,
                                    mySquare.getMyPaintBorder());
                        }
                    }
                    break;

            }

        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        float tmp;
        for (MySquare mySquare : mySquares) {
            tmp = mySquare.getX();
            mySquare.setX(mySquare.getY());
            mySquare.setY(tmp);
        }
    }
}