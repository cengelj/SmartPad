import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockListener implements ActionListener {
	Clockable f;
	ClockListener(Clockable c)
	{
		f = c;
	}
	
	public void actionPerformed(ActionEvent e) {
            try {
				f.clock();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}

}
