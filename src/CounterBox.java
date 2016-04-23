import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class CounterBox extends JPanel {

	
	public CounterBox(){
		ImageIcon upIcon = new ImageIcon("up.png");
		Image upImage = upIcon.getImage().getScaledInstance(37, 37, 0);
		upIcon = new ImageIcon(upImage);
		ImageIcon downIcon = new ImageIcon("down.png");
		Image downImage = downIcon.getImage().getScaledInstance(37, 37, 0);
		downIcon = new ImageIcon(downImage);
		
		setLayout(new FlowLayout());
		
		
		
	}
}
