package group8.appointmentbook.testclasses;

import javax.swing.*;

/**
 * This will display 12 images in separate windows.
 * 
 * @author Caroline Castonguay-Boisvert
 * @author Natacha Gabbamonte
 * @author Kim Parisé
 * 
 */
public class ImageViewer {

	public ImageViewer(ImageIcon image) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel(image);
		frame.getContentPane().add(label);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ImageViewer(new ImageIcon("images/month1.gif"));
		new ImageViewer(new ImageIcon("images/month2.gif"));
		new ImageViewer(new ImageIcon("images/month3.gif"));
		new ImageViewer(new ImageIcon("images/month4.gif"));
		new ImageViewer(new ImageIcon("images/month5.gif"));
		new ImageViewer(new ImageIcon("images/month6.gif"));
		new ImageViewer(new ImageIcon("images/month7.gif"));
		new ImageViewer(new ImageIcon("images/month8.gif"));
		new ImageViewer(new ImageIcon("images/month9.gif"));
		new ImageViewer(new ImageIcon("images/month10.gif"));
		new ImageViewer(new ImageIcon("images/month11.gif"));
		new ImageViewer(new ImageIcon("images/month12.gif"));

	}

}
