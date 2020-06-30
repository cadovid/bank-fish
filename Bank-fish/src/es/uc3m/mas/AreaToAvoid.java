package es.uc3m.mas;
//An area to avoid for fish. It disappears automatically after a while
public class AreaToAvoid extends FBObject {
    protected double radius;
    protected int timeLeft = 1000;
    public AreaToAvoid(double _x, double _y, double _radius) {
        posX = _x;
        posY = _y;
        radius = _radius;
    }
    public double getRadius() {
        return radius;
    }
    public void update() {
        timeLeft--;
    }
    public boolean isDead() {
        return timeLeft <= 0;
    }
}