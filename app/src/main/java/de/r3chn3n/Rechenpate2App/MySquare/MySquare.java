package de.r3chn3n.Rechenpate2App.MySquare;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MySquare implements Comparable<MySquare> {

    private MySquareStore mySquareStore = new MySquareStore();
    public int devicePixelsWidthY =  Resources.getSystem().getDisplayMetrics().heightPixels;
    public int devicePixelsWidthX =  Resources.getSystem().getDisplayMetrics().widthPixels;
    float deviceActualDpiX = Resources.getSystem().getDisplayMetrics().xdpi ;
    float deviceActualDpiY= Resources.getSystem().getDisplayMetrics().ydpi ;
    float deviceActualInchWidthX = devicePixelsWidthX / deviceActualDpiX ;
    float deviceActualInchWidthY = devicePixelsWidthY / deviceActualDpiY ;
    public float scale = 0 ;
    public int BLUE = Color.parseColor("#000ffa");
    public int RED = Color.parseColor("#ff0057");
    public int BLUE_BORDER = Color.parseColor("#2e3bff");
    public int RED_BORDER = Color.parseColor("#ff024d");
    public int OFFSET ;
    public float LENGTH  ;
    public float LENGTH_BORDER ;
    public float STROKE_WIDTH ; //6F ;

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
    int n;

    MySquare(float x, float y) {
        this.x = x;
        this.y = y;
        if (deviceActualInchWidthX < deviceActualInchWidthY) {
            if (deviceActualInchWidthY < 5) {
                n = 12;
            } else {
                n = 18;
            }
            scale = (float) devicePixelsWidthX / (n  * Resources.getSystem().getDisplayMetrics().density)  ;
        } else {
            if (deviceActualInchWidthX < 5) {
                n = 12;
            } else {
                n = 18;
            }
            scale = (float) devicePixelsWidthY / (n  * Resources.getSystem().getDisplayMetrics().density)  ;

        }
        OFFSET = (int) (scale );
        LENGTH = scale ;
        LENGTH_BORDER = scale ;
        STROKE_WIDTH = scale / 5; //6F ;
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
