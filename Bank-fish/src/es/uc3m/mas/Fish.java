package es.uc3m.mas;
import java.util.ArrayList;
//A fish, managed by an agent
public class Fish extends FBObject {
    // Constants
    public static final double STEP = 3;
    public static final double DISTANCE_MIN = 5;
    public static final double DISTANCE_MIN_SQUARE = 25;
    public static final double DISTANCE_MAX = 40;
    public static final double DISTANCE_MAX_SQUARE = 1600;
    // Attributes
    protected double speedX;
    protected double speedY;
    // Methods
    public Fish(double _x, double _y, double _dir) {
        posX = _x;
        posY = _y;
        speedX = Math.cos(_dir);
        speedY = Math.sin(_dir);
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    protected void updatePosition() {
        posX += STEP * speedX;
        posY += STEP * speedY;
    }
    protected boolean inAlignment(Fish p) {
        double distanceSquare = distanceSquare(p);
        return (distanceSquare < DISTANCE_MAX_SQUARE && distanceSquare > DISTANCE_MIN_SQUARE);
    }
    protected double distanceToTheWall(double wallXMin, double wallYMin, double wallXMax, double wallYMax) {
        double min = Math.min(posX - wallXMin, posY - wallYMin);
        min = Math.min(min, wallXMax - posX);
        min = Math.min(min, wallYMax - posY);
        return min;
    }
    protected void normalize() {
        double longueur = Math.sqrt(speedX * speedX + speedY * speedY);
        speedX /= longueur;
        speedY /= longueur;
    }
    protected boolean avoidWalls(double wallXMin, double wallYMin, double wallXMax, double wallYMax) {
// We stop at the walls
        if (posX < wallXMin) {
            posX = wallXMin;
        } else if (posY < wallYMin) {
            posY = wallYMin;
        } else if (posX > wallXMax) {
            posX = wallXMax;
        } else if (posY > wallYMax) {
            posY = wallYMax;
        }
// Change direction
        double distance = distanceToTheWall(wallXMin, wallYMin, wallXMax, wallYMax);
        if (distance < DISTANCE_MIN) {
            if (distance == (posX - wallXMin)) {
                speedX += 0.3;
            } else if (distance == (posY - wallYMin)) {
                speedY += 0.3;
            } else if (distance == (wallXMax - posX)) {
                speedX -= 0.3;
            } else if (distance == (wallYMax - posY)) {
                speedY -= 0.3;
            }
            normalize();
            return true;
        }
        return false;
    }
    protected boolean avoidObstacles(ArrayList<AreaToAvoid> obstacles) {
        if (!obstacles.isEmpty()) {
// Finding the nearest obstacle
            AreaToAvoid nearObstacle = obstacles.get(0);
            double distanceSquare = distanceSquare(nearObstacle);
            for (AreaToAvoid o : obstacles) {
                if (distanceSquare(o) < distanceSquare) {
                    nearObstacle = o;
                    distanceSquare = distanceSquare(o);
                }
            }
            if (distanceSquare < (nearObstacle.radius * nearObstacle.radius)) {
// If collision, calculation of the diff vector
                double distance = Math.sqrt(distanceSquare);
                double diffX = (nearObstacle.posX - posX) / distance;
                double diffY = (nearObstacle.posY - posY) / distance;
                speedX = speedX - diffX / 2;
                speedY = speedY - diffY / 2;
                normalize();
                return true;
            }
        }
        return false;
    }
    protected boolean avoidFish(Fish[] fishList) {
// Find the nearest fish
        Fish p;
        if (!fishList[0].equals(this)) {
            p = fishList[0];
        } else {
            p = fishList[1];
        }
        double distanceSquare = distanceSquare(p);
        for (Fish fish : fishList) {
            if (distanceSquare(fish) < distanceSquare && !fish.equals(this)) {
                p = fish;
                distanceSquare = distanceSquare(p);
            }
        }
// Avoidance
        if (distanceSquare < DISTANCE_MIN_SQUARE) {
            double distance = Math.sqrt(distanceSquare);
            double diffX = (p.posX - posX) / distance;
            double diffY = (p.posY - posY) / distance;
            speedX = speedX - diffX / 4;
            speedY = speedY - diffY / 4;
            normalize();
            return true;
        }
        return false;
    }
    protected void calculateAverageDirection(Fish[] fishList) {
        double speedXTotal = 0;
        double speedYTotal = 0;
        int nbTotal = 0;
        for (Fish p : fishList) {
            if (inAlignment(p)) {
                speedXTotal += p.speedX;
                speedYTotal += p.speedY;
                nbTotal++;
            }
        }
        if (nbTotal >= 1) {
            speedX = (speedXTotal / nbTotal + speedX) / 2;
            speedY = (speedYTotal / nbTotal + speedY) / 2;
            normalize();
        }
    }
    protected void update(Fish[] fishList, ArrayList<AreaToAvoid> obstacles, double width, double height) {
        if (!avoidWalls(0, 0, width, height)) {
            if (!avoidObstacles(obstacles)) {
                if (!avoidFish(fishList)) {
                    calculateAverageDirection(fishList);
                }
            }
        }
        updatePosition();
    }
}