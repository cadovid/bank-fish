package es.uc3m.mas;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
// The ocean in which fish swim
public class Ocean extends Observable {
    // Attributes
    protected Fish[] fishList;
    protected ArrayList<AreaToAvoid> obstacles;
    protected Random generator;
    protected double width;
    protected double height;
    // Methods
    public Ocean(int _nbFish, double _width, double _height) {
        width = _width;
        height = _height;
        generator = new Random();
        obstacles = new ArrayList<AreaToAvoid>();
        fishList = new Fish[_nbFish];
        for (int i = 0; i < _nbFish; i++) {
            fishList[i] = new Fish(generator.nextDouble() * width, generator.nextDouble() * height,
                    generator.nextDouble() * 2 * Math.PI);
        }
    }
    public void addObstacle(double _posX, double _posY, double raduis) {
        obstacles.add(new AreaToAvoid(_posX, _posY, raduis));
    }
    protected void updateObstacles() {
        for (AreaToAvoid obstacle : obstacles) {
            obstacle.update();
        }
        obstacles.removeIf(o -> o.isDead());
    }
    protected void updateFishList() {
        for (Fish p : fishList) {
            p.update(fishList, obstacles, width, height);
        }
    }
    public void updateOcean() {
        updateObstacles();
        updateFishList();
        setChanged();
        notifyObservers();
    }
}
