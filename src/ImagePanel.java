import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class ImagePanel extends JPanel {
	
	//private BufferedImage image;
	private ImageIcon photo;
	JLabel picLabel;

	public ImagePanel(ImageIcon image) {
		this.photo = image;
		picLabel = new JLabel(photo);
		add(picLabel);
	}
	
}
