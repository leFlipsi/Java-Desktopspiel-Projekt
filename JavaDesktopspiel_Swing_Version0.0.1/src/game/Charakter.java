package game;

import javax.swing.JPanel;

import image.LoadImage;
import main.Window;

public class Charakter extends JPanel {
	private LoadImage char_img;
	private int width;

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
