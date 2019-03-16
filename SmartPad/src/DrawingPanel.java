import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener, Clockable {
    private ArrayList<Point> toDraw;
    private Timer t;
    private JToolBar toolBar;
    private ArrayList<JButton> buttons;
    private int action;
    private JBColor color;
    private Ellipse2D mouseCursor;

    public DrawingPanel(int width, int height) {
        mouseCursor = null;
        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        color = JBColor.BLACK;
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
        JButton connect = new JButton("Connect");
        connect.addActionListener(new ButtonListener(connect));
        JButton colorButt = new JButton("Color");
        colorButt.addActionListener(new ColorListener(colorButt));
        colorButt.setBackground(JBColor.BLACK);
        buttons.add(colorButt);
        buttons.add(connect);
        toolBar.setPreferredSize(new Dimension(500, 50));
        toolBar.add(erase);
        toolBar.add(draw);
        toolBar.add(colorButt);
        toolBar.add(connect);
        this.add(toolBar);
        action = 1;
        t = new Timer(2, new ClockListener(this));
        t.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(toolBar.contains(e.getPoint()))return;
        draw(e.getPoint(), 5);
        System.out.println("Mouse clicked at:\t" + e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at:\t" + e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(action == 0) {
            Graphics2D g2 = ((Graphics2D) getGraphics());
            if (mouseCursor != null) {
                g2.setColor(getBackground());
                g2.draw(mouseCursor);
            }
        }
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
        Color x = buttons.get(2).getBackground();
        color = (JBColor)x;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(toolBar.contains(e.getPoint()))return;
        System.out.println("Mouse dragged at:\t" + e.getPoint());
        if(action == 0) {
            Graphics2D g2 = ((Graphics2D) getGraphics());
            if (mouseCursor != null) {
                g2.setColor(getBackground());
                g2.draw(mouseCursor);
            }
            g2.setColor(color);
            mouseCursor = new Ellipse2D.Double(e.getX(), e.getY(), 20, 20);
            g2.draw(mouseCursor);
            draw(e.getPoint(), 20);
        }
        draw(e.getPoint(), 5);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
        protected ToggleListener(JButton buttonOne, JButton buttonTwo){
            this.buttonOne = buttonOne;
            this.buttonTwo = buttonTwo;
            action = 1;
        }
        public void actionPerformed(ActionEvent e) {
            if(((JButton)e.getSource()).getName().equals("e")){
                action = 0;
                buttonOne.setBackground(JBColor.LIGHT_GRAY);
                buttonTwo.setBackground(JBColor.WHITE);
            }
            else{
                action = 1;
                buttonOne.setBackground(JBColor.WHITE);
                buttonTwo.setBackground(JBColor.LIGHT_GRAY);

            }
        }
        public int getAction(){
            return action;
        }
    }
    class ButtonListener implements ActionListener{
        private JButton button;
        boolean active;
        public ButtonListener(JButton button){
            this.button = button;
            active = false;
        }

        public void actionPerformed(ActionEvent e) {
            if(!active){
                // CREATE THE CONNECTION
                System.out.println("Connected");
            }
            else{
                // DESTROY THE CONNECTION
                System.out.println("Disconnected");
            }
            active = !active;
        }
    }
    class ColorListener implements ActionListener{
        private JButton button;
        public ColorListener(JButton button){
            this.button = button;
        }
        public void actionPerformed(ActionEvent e) {
            String[] choices = {"red", "orange", "yellow", "green", "blue", "white", "black"};
            int choice = JOptionPane.showOptionDialog(button.getRootPane(), "Choose a new color", "Color", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, null);
            switch(choice){
                case 0:
                    button.setBackground(JBColor.RED);
                    break;
                case 1:
                    button.setBackground(JBColor.ORANGE);
                    break;
                case 2:
                    button.setBackground(JBColor.YELLOW);
                    break;
                case 3:
                    button.setBackground(JBColor.GREEN);
                    break;
                case 4:
                    button.setBackground(JBColor.BLUE);
                    break;
                case 5:
                    button.setBackground(JBColor.WHITE);
                    break;
                case 6:
                    button.setBackground(JBColor.BLACK);
                    break;
            }
        }
    }
}
