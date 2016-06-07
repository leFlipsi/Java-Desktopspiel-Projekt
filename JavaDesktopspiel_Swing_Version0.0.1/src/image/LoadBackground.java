package image;

import java.util.ArrayList;

import javax.swing.JPanel;

import main.Window;

public class LoadBackground extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<LoadImage> images;
	private int x_length, y_length, width, load;
	private ArrayList<Integer> item_i, item_j;
	private double[][] data;
	private LoadImage stein_item;

	public LoadBackground(Window window, double[][] data) {
		this.x_length = data[0].length;
		this.y_length = data.length;
		this.width = 64;
		this.load = 16;
		this.data = data;
		this.setLayout(null);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				try {
					if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.0) {
						images.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
								(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
					} else if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.1) {
						images.add(new LoadImage(j * width, i * width, width, 4 * load, 6 * load, load, "sprite.png",
								this, false));
						item_i.add(i);
						item_j.add(j);
					}
				} catch (Exception e) {

				}
			}
		}
		this.setSize(this.x_length * this.width, this.y_length * this.width);
		this.setLocation(0, 0);
		this.setVisible(true);
		window.add(this);
	}

	public void resetScaling() {
		for (int i = 0; i < y_length; i++) {
			for (int j = 0; j < x_length; j++) {
				images.get(i * j); // TODO
			}
		}
	}

	public void resetPosition(int x_dir, int y_dir) {
		this.setLocation(this.getLocation().x + x_dir, this.getLocation().y + y_dir);
	}

	public int getXLength() {
		return x_length;
	}

	public int getYLength() {
		return y_length;
	}

	public int getScale() {
		return width;
	}

	public double getData(int y, int x) {
		return data[y][x];
	}

	public void resetBackground(int count) {
		try {
			images.set(item_i.get(count) * item_j.get(count), null);
			images.set(item_i.get(count) * item_j.get(count),
					new LoadImage(item_j.get(count) * width, item_i.get(count) * width, width,
							(int) data[item_i.get(count)][item_j.get(count)] % 8 * load,
							(int) Math.floor(data[item_i.get(count)][item_j.get(count)] / 8) * load, load, "sprite.png",
							this, false));
		} catch (Exception e) {

		}
	}
}
