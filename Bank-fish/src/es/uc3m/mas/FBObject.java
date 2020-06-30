package es.uc3m.mas;
//Object in the world (obstacle or fish)
public class FBObject {
    public double posX;
    public double posY;
    public FBObject() {
    }
    public FBObject(double _x, double _y) {
        posX = _x;
        posY = _y;
    }
    public double distance(FBObject o) {
        return Math.sqrt((o.posX - posX) * (o.posX - posX) + (o.posY - posY) * (o.posY - posY));
    }
    public double distanceSquare(FBObject o) {
        return Math.pow((o.posX - posX), 2) + Math.pow((o.posY - posY), 2);
    }
}
