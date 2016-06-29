package game;

import javax.swing.JPanel;

import image.LoadImage;
import main.Window;

/**
 * Inventar Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class Inventar extends JPanel {
	private LoadImage inventar_img;
	private int width;

	/**
	 * @param window Fensterobjekt
	 */
	public Inventar(Window window) {
		this.width = 48;
		this.setOpaque(false);
		this.setLayout(null);
		this.setSize(this.width, this.width);
		this.setLocation(window.getWidth() - 96, window.getHeight() - 112);
		inventar_img = new LoadImage(0, 0, this.width, 0, 64, 16, "sprite.png", this, true);
		this.add(inventar_img);
		this.setVisible(true);
		window.add(this);
	}
}
