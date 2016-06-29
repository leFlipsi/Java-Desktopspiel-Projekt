package game;

import javax.swing.JPanel;

import image.LoadImage;
import main.Window;

/**
 * Item Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class Item extends JPanel {
	private LoadImage inventar_img;
	private int width;

	/**
	 * @param window Fensterobjekt
	 * @param load_x Vielfaches von 16 - wählt das gewünschte Bild dieser Variable nach aus der Sprite-Datei aus
	 */
	public Item(Window window, int load_x) {
		this.width = 48;
		this.setOpaque(false);
		this.setLayout(null);
		this.setSize(this.width, this.width);
		this.setLocation(window.getWidth() - 96, window.getHeight() - 112);
		inventar_img = new LoadImage(0, 0, this.width, load_x, 64, 16, "sprite.png", this, true);
		this.add(inventar_img);
		this.setVisible(true);
		window.add(this);
	}
}
