package de.r3chn3n.Rechenpate2App.MySquare;

public abstract class MySquareFactory {

    public abstract MySquare createMySquare(float maxLength, float val,  float x, float y);

    public MySquare orderMySquare(float maxLength, float val, float x, float y) {
        MySquare mySquare = createMySquare(maxLength, val, x, y);
        if (mySquare == null) {
            System.out.println("Sorry, we are not able to create this kind of mySquare\n");
            return null;
        }
        return mySquare;
    }
}
