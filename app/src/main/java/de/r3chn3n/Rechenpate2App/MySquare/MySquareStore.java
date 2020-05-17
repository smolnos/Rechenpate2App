package de.r3chn3n.Rechenpate2App.MySquare;

public class MySquareStore extends MySquareFactory {
    @Override
    public MySquare createMySquare(float maxLength, float x, float y) {
        if (y < 0.33 * maxLength) {
            return new My100Square(x, y);
        } else if (y < 0.66 * maxLength){
            return new My10Square(x, y);
        } else {
            return new My1Square(x, y);
        }
    }
}
