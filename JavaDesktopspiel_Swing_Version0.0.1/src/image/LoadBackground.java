package image;

import java.util.ArrayList;

import javax.swing.JPanel;

import main.Window;

public class LoadBackground extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<LoadImage> images;
	private int x_length, y_length, width;
	private double[][] data;

	public LoadBackground(Window window, double[][] data) {
		this.x_length = data[0].length;
		this.y_length = data.length;
		this.width = 64;
		this.data = data;
		this.setLayout(null);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				try {
					images.add(new LoadImage(j * window.getWidth() / 16, i * window.getHeight() / 9, 64,
							(int) (data[i][j] % 2) * 33, (int) Math.floor(data[i][j] / 2) * 33, 31, "test_sprite.jpg",
							this, false));
				} catch (Exception e) {

				}
			}
		}
		this.setSize(this.x_length*this.width, this.y_length*this.width);
		this.setLocation(0, 0);
		this.setVisible(true);
		window.add(this);
	}

	public void resetScaling() {
		for (int i = 0; i < y_length; i++) {
			for (int j = 0; j < x_length; j++) {
				images.get(i*j); //TODO
			}
		}
	}

	public void resetPosition(int x_dir, int y_dir) {
		this.setLocation(this.getLocation().x + x_dir, this.getLocation().y + y_dir);
	}
	
	public int getXLength(){
		return x_length;
	}
	
	public int getYLength(){
		return y_length;
	}
	
	public int getScale(){
		return width;
	}
	
	public double getData(int y, int x){
		return data[y][x];
	}
}
