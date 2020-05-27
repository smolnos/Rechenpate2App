package de.r3chn3n.Rechenpate2App.MySquare;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MySquare implements Comparable<MySquare> {

    private MySquareStore mySquareStore = new MySquareStore();
    public static final float scale = Math.min(Resources.getSystem().getDisplayMetrics().heightPixels, Resources.getSystem().getDisplayMetrics().widthPixels) / (Resources.getSystem().getDisplayMetrics().density * 15 );

    public final int OFFSET = (int) (scale );
    public static final float LENGTH = scale ;
    public static final float LENGTH_BORDER = scale ;
    public static final float STROKE_WIDTH = scale / 5; //6F ;
    public static final int BLUE = Color.parseColor("#000ffa");
    public static final int RED = Color.parseColor("#ff0057");
    public static final int BLUE_BORDER = Color.parseColor("#2e3bff");
    public static final int RED_BORDER = Color.parseColor("#ff024d");

    protected float x;
    protected float y;
    protected float xFirstTouch;
    protected float yFirstTouch;
    protected Paint myPaint;
    protected Paint myPaintBorder;
    protected String color;
    protected Element element;
    protected float squareWidth;
    private float squareHeight;
    protected float numOfSquaresX;
    protected float numOfSquaresY;
    protected boolean selectedIndex = false;
    protected boolean createElement = false;

    MySquare(float x, float y) {
        this.x = x;
        this.y = y;
        this.xFirstTouch = x;
        this.yFirstTouch = y;
        this.myPaint = new Paint();
        this.myPaint.setStyle(Paint.Style.FILL);
        this.myPaint.setColor(BLUE);
        this.myPaint.setAntiAlias(true);
        this.myPaintBorder = new Paint();
        this.myPaintBorder.setStyle(Paint.Style.STROKE);
        this.myPaintBorder.setStrokeWidth(STROKE_WIDTH);
        this.myPaintBorder.setAntiAlias(true);
        this.setCreateElement(true);
    }

    public abstract void setColor();

    /**
     * check if current event touched this rectangle
     *
     * @param event instance of class Motionevent
     * @return true if event with point (eventX, eventY) is within rectangle
     */
    public abstract boolean isEventInSquare(MotionEvent event);

    /**
     * check if the given point is out of the canvas
     *
     * @param displayWidth width of the current display
     * @param displayHeight height of the current display
     * @return true if x value or y value are below offset or greater than width/ height
     */
    public abstract boolean outOfScreen(float displayWidth, float displayHeight);

    /**
     * change color of this rectangle if the event just touched this rectangle and does not move in
     * another direction
     *
     * @param event parameter of the new event needed to compute new position of this rectangle
     * @return true if event with point (eventX, eventY) is still within rectangle and thus only color
     * should be changed and not the position
     */
    public boolean shouldColorBeChanged(MotionEvent event, float oldEventX, float oldEventY) {
        return event.getX() - oldEventX <= LENGTH &&
                event.getX() - oldEventX >= -LENGTH &&
                event.getY() - oldEventY <= LENGTH &&
                event.getY() - oldEventY >= -LENGTH;
    }

    /**
     * set new position due to the new event to this circle
     * @param event paramer of the new event needed to compute new position of this circle
     */
    public void moveSquare(MotionEvent event, float oldEventX, float oldEventY) {
        setX(getXFirstTouch() + event.getX() - oldEventX);
        setY(getYFirstTouch() + event.getY() - oldEventY);
    }

    @Override
    public int compareTo(MySquare ms) {
        if (element == null || ms.element == null) {
            return 0;
        }
        return element.compareTo(ms.element);
    }
}
