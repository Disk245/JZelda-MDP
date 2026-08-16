package view;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class GamePanel extends JPanel implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		
		
	}
	
	public void paintComponent(Graphics2D g2d) {
		super.paintComponent(g2d);
	}

}
