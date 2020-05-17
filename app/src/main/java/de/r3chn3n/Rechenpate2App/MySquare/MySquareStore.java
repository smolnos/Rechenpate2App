package de.r3chn3n.Rechenpate2App.MySquare;

public class MySquareStore extends MySquareFactory {
    @Override
    public MySquare createMySquare(float maxLength, float val, float x, float y) {
        if (val < 0.33 * maxLength) {
            return new My100Square(x, y);
        } else if (val < 0.66 * maxLength){
            return new My10Square(x, y);
        } else {
            return new My1Square(x, y);
        }
    }
}
