import com.intellij.ui.JBColor;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener, Clockable {
    private ArrayList<Point> toDraw;
    private Timer t;
    private JToolBar toolBar;
    private ArrayList<JButton> buttons;
    private int action;
    private Color color;
    private Circle mouseCursor;
    public DrawingPanel(int height, int width) {
        this.setPreferredSize(new Dimension(height, width));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        color = Color.BLACK;
        buttons = new ArrayList<>();
        toolBar = new JToolBar();
        JButton erase = new JButton("Erase");
        erase.setName("e");
        JButton draw = new JButton("Draw");
        draw.setName("d");
        ToggleListener tog = new ToggleListener(erase, draw);
        erase.addActionListener(tog);
        draw.addActionListener(tog);
        buttons.add(erase);
        buttons.add(draw);
        toolBar.setPreferredSize(new Dimension(500, 50));
        toolBar.add(erase);
        toolBar.add(draw);
        this.add(toolBar);
        action = 1;
        t = new Timer(5, new ClockListener(this));
        t.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        draw(e.getPoint(), 5);
        System.out.println("Mouse clicked at:\t" + e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at:\t" + e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released at:\t" + e.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered at:\t" + e.getPoint());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited at:\t" + e.getPoint());
    }

    public void clock() {
        action = ((ToggleListener)buttons.get(0).getActionListeners()[0]).action;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged at:\t" + e.getPoint());
        draw(e.getPoint(), 5);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseCursor.setCenterX(e.getPoint().getX());
        mouseCursor.setCenterY(e.getPoint().getY());
        mouseCursor.resize(50.0, 50.0);
        //getGraphics().drawPolygon(mouseCursor);
        // System.out.println("Mouse moved at:\t" + e.getPoint());
    }
    private void draw(Point p, int size){
        Graphics2D g = (Graphics2D)getGraphics();
        if(action != 0)g.setColor(color);
        else g.setColor(this.getBackground());
        g.fillOval(p.x, p.y, size, size);
    }
    class ToggleListener implements ActionListener{
        private int action;
        private JButton buttonOne;
        private JButton buttonTwo;
        public ToggleListener(JButton buttonOne, JButton buttonTwo){
            buttonOne.addActionListener(this);
            buttonTwo.addActionListener(this);
            this.buttonOne = buttonOne;
            this.buttonTwo = buttonTwo;
            action = 1;
        }
        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getName().equals("e")){
                action = 0;
                buttonOne.setBackground(JBColor.WHITE);
                buttonTwo.setBackground(JBColor.LIGHT_GRAY);
            }
            else{
                action = 1;
                buttonOne.setBackground(JBColor.LIGHT_GRAY);
                buttonTwo.setBackground(JBColor.WHITE);
            }
        }
        public int getAction(){
            return action;
        }
    }
}
