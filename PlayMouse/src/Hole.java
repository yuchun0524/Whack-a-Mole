import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hole{
	JLabel holeLabel = new JLabel();
	BufferedImage hole_img= null;
	
	
	public Hole(int x,int y)
	{
		holeLabel.setLocation(x-30,y-10);
		holeLabel.setSize(150,80);
		holeLabel.setVisible(true);
		//Read the picture as a BufferedImage
		try {
			File F = new File("./src/images/hole.png");
			hole_img = ImageIO.read(F);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		//Resize the BufferedImage
		Image hole_dimg = hole_img.getScaledInstance(holeLabel.getWidth(), holeLabel.getHeight(),Image.SCALE_SMOOTH);
		//Create an ImageIcon
		ImageIcon hole_imageIcon = new ImageIcon(hole_dimg);
		holeLabel.setIcon(hole_imageIcon);
	}
}
