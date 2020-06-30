package es.uc3m.mas;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
// The display of our simulation
public class OceanJPanel extends JPanel implements Observer, MouseListener {
    protected Ocean ocean;
    protected Timer timer;
    public OceanJPanel() {
        this.setBackground(new Color(31, 204, 255));
        this.addMouseListener(this);
    }
    public void Lancer() {
        ocean = new Ocean(600, this.getWidth(), getHeight());
        ocean.addObserver(this);
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                ocean.updateOcean();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(tache, 0, 15);
    }
    protected void DessinerPoisson(Fish p, Graphics g) {
        g.drawLine((int) p.posX, (int) p.posY, (int) (p.posX - 10 * p.speedX), (int) (p.posY - 10 * p.speedY));
    }
    protected void DessinerObstacle(AreaToAvoid o, Graphics g) {
        g.drawOval((int) (o.posX - o.radius), (int) (o.posY - o.radius), (int) o.radius * 2, (int) o.radius * 2);
    }
    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Fish p : ocean.fishList) {
            DessinerPoisson(p, g);
        }
        for (AreaToAvoid o : ocean.obstacles) {
            DessinerObstacle(o, g);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        ocean.addObstacle(e.getX(), e.getY(), 20);
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}