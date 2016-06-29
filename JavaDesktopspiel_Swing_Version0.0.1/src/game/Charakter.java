package game;

import javax.swing.JPanel;

import image.LoadImage;
import main.Window;

/**
 * Charakter Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class Charakter extends JPanel {
	private LoadImage char_img;
	private int width;

	/**
	 * @param window Fensterobjekt
	 * @param x X-Position im Fenster
	 * @param y Y-Position im Fenster
	 */
	public Charakter(Window window, int x, int y) {
		this.width = 64;
		this.setOpaque(false);
		this.setLayout(null);
		this.setSize(this.width, this.width);
		this.setLocation(window.getWidth() / 2 - this.width / 2, window.getHeight() / 2 - this.width / 2);
		char_img = new LoadImage(0, 0, this.width, x, y, this.width / 4, "charakter.png", this, true);
		this.add(char_img);
		this.setVisible(true);
		window.add(this);
	}

}
