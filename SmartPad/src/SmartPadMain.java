import javax.swing.*;
import java.awt.*;

public class SmartPadMain extends JFrame {
    public static void main(String[] args) {
        SmartPadMain window = new SmartPadMain();
        window.setPreferredSize(new Dimension(500, 500));
        window.setTitle("SmartPad, be smart - don't do drugs kids");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel p = new DrawingPanel(500, 500);
        window.setContentPane(p);
        window.pack();
        window.setVisible(true);
    }
}