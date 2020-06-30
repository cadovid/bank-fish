package es.uc3m.mas;
import javax.swing.JFrame;
// Launching the window and the application
public class Application {
    public static void main(String[] args) {
// Creation of the window
        JFrame window = new JFrame();
        window.setTitle("Fish Bank");
        window.setSize(650, 650);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
// Creation of the content
        OceanJPanel panel = new OceanJPanel();
        window.setContentPane(panel);
// Display
        window.setVisible(true);
        panel.Lancer();
    }
}
