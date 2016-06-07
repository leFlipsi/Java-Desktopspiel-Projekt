package game;

import javax.swing.JPanel;

import image.LoadImage;
import main.Window;

public class Item extends JPanel {
	private LoadImage inventar_img;
	private Window window;
	private int width;

	public Item(Window window, int load_x) {
		this.window = window;
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
