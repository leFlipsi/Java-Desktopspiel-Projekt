package image;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Window;

public class LoadBackground extends JPanel {
	private ArrayList<LoadImage> images;
	private int x_length, y_length, width;

	public LoadBackground(Window window, double[][] data) {
		this.x_length = data[0].length;
		this.y_length = data.length;
		this.width = 64;
		this.setLayout(null);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				try {
					images.add(new LoadImage(j * window.getWidth() / 16, i * window.getHeight() / 9, 64,
							(int) (data[i][j] % 2) * 32, (int) Math.floor(data[i][j] / 2) * 32, 32, "test_sprite.jpg",
							this));
				} catch (Exception e) {

				}
			}
		}
		this.setSize(this.x_length*this.width, this.y_length*this.width);
		this.setLocation(-100, 0);
		this.setVisible(true);
		window.add(this);
	}

	public void resetScaling() {
		for (int i = 0; i < y_length; i++) {
			for (int j = 0; j < x_length; j++) {
				images.get(i*j);
			}
		}
	}

	public void resetPosition(int x_dir, int y_dir) {
		
	}
}
