package de.r3chn3n.Rechenpate2App;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.r3chn3n.Rechenpate2App.MySquare.MySquare;
import de.r3chn3n.Rechenpate2App.MySquare.MySquareStore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaintView extends View {

    private float oldEventX;
    private float oldEventY;
    int indexMySquares = 0;
    boolean changed = false;
    private List<MySquare> mySquares = new ArrayList<>();
    private MySquareStore mySquareStore = new MySquareStore();

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
                oldEventX = event.getX();
                oldEventY = event.getY();
                if (isSquareNotPresent(event)) {
                    addNewSquare(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                MySquare mySquare = mySquares.get(indexMySquares);
                mySquares.get(indexMySquares).setXFirstTouch(mySquares.get(indexMySquares).getX());
                mySquares.get(indexMySquares).setYFirstTouch(mySquares.get(indexMySquares).getY());
                if (mySquare.outOfScreen(getWidth(), getHeight())) {
                    deleteSquare();
                } else if (mySquare.shouldColorBeChanged(event, oldEventX, oldEventY)) {
                    mySquares.get(indexMySquares).setColor();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mySquares.get(indexMySquares).moveSquare(event, oldEventX, oldEventY);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * check if new touch is whithin a rectangle. If so, set indexMySquares to this rectangle
     * and return that that rectangle is already present, i.e. false. If no, just return true
     * that that rectangle is not present
     * @return boolean true if rectangle is not in list mySquares
     */
    private boolean isSquareNotPresent(MotionEvent event) {
        boolean circleNotPresent = true;
        int i = 0;
        for (MySquare ms : mySquares) {
            if (ms.isEventInSquare(event)) {
                circleNotPresent = false;
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
        float maxLength = getWidth() > getHeight() ? getWidth() : getHeight();
        MySquare newSquare = mySquareStore.orderMySquare(maxLength, event.getX(), event.getY());
        mySquares.add(newSquare);
        Collections.sort(mySquares);
        setNewIndexMySquares();
        mySquares.get(indexMySquares).setSelectedIndex(false);
    }

    private void setNewIndexMySquares() {
        for (int i = 0; i < mySquares.size(); i++) {
            if (mySquares.get(i).isSelectedIndex()) {
                indexMySquares = i;
                break;
            }
        }
    }

    /**
     * delete circles if center is outside the screen
     */
    private void deleteSquare() {
        if (mySquares.isEmpty()) return;
        mySquares.remove(indexMySquares);
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
                    draw1Square(canvas, mySquare);
                    break;
                case Tens:
                    for (int i = 0; i < 10; i++) {
                        draw1SquareOutOf10(canvas, mySquare, i);
                    }
                    break;
                case Hundreds:
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            draw1SquareOutOf100(canvas, mySquare, i, j);
                        }
                    }
                    break;
            }
        }
        invalidate();
    }

    private void draw1SquareOutOf100(Canvas canvas, MySquare mySquare, int i, int j) {
        float newYPos =  mySquare.getY() - 2 * MySquare.LENGTH_BORDER * 5;
        float newXPos =  mySquare.getX() - 2 * MySquare.LENGTH_BORDER * 5;
        float left = newXPos - MySquare.LENGTH_BORDER + (2 * j) * MySquare.LENGTH_BORDER + (j - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        float top = newYPos - MySquare.LENGTH_BORDER + (2 * i) * MySquare.LENGTH_BORDER + (i - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        float right = newXPos + MySquare.LENGTH_BORDER + (2 * j) * MySquare.LENGTH_BORDER + (j - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        float bottom = newYPos + MySquare.LENGTH_BORDER + (2 * i) * MySquare.LENGTH_BORDER  + (i - 1) *  MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaint());

        left = newXPos - MySquare.LENGTH + (2 * j) * MySquare.LENGTH + (j - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        top = newYPos - MySquare.LENGTH + (2 * i) * MySquare.LENGTH + (i - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        right = newXPos + MySquare.LENGTH + (2 * j) * MySquare.LENGTH + (j - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        bottom = newYPos + MySquare.LENGTH + (2 * i) * MySquare.LENGTH  + (i - 1) *  MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaintBorder());
    }

    private void draw1SquareOutOf10(Canvas canvas, MySquare mySquare, int i) {
        float newYPos =  mySquare.getY() - 2 * MySquare.LENGTH_BORDER * 5;
        float left = mySquare.getX() - MySquare.LENGTH_BORDER;
        float top = newYPos - MySquare.LENGTH_BORDER + (2 * i) * MySquare.LENGTH_BORDER + (i - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        float right = mySquare.getX() + MySquare.LENGTH_BORDER;
        float bottom = newYPos + MySquare.LENGTH_BORDER + (2 * i) * MySquare.LENGTH_BORDER  + (i - 1) *  MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaint());

        left = mySquare.getX() - MySquare.LENGTH;
        top = newYPos - MySquare.LENGTH + (2 * i) * MySquare.LENGTH + (i - 1) * MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        right = mySquare.getX() + MySquare.LENGTH;
        bottom = newYPos + MySquare.LENGTH + (2 * i) * MySquare.LENGTH  + (i - 1) *  MySquare.STROKE_WIDTH / 2 +  MySquare.STROKE_WIDTH;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaintBorder());
    }

    private void draw1Square(Canvas canvas, MySquare mySquare) {
        float left = mySquare.getX() - MySquare.LENGTH_BORDER;
        float top = mySquare.getY() - MySquare.LENGTH_BORDER;
        float right = mySquare.getX() + MySquare.LENGTH_BORDER;
        float bottom = mySquare.getY() + MySquare.LENGTH_BORDER;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaint());

        left = mySquare.getX() - MySquare.LENGTH;
        top = mySquare.getY() - MySquare.LENGTH;
        right = mySquare.getX() + MySquare.LENGTH;
        bottom = mySquare.getY() + MySquare.LENGTH;
        canvas.drawRect(left, top, right, bottom, mySquare.getMyPaintBorder());
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