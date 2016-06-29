package image;

import java.util.ArrayList;

import javax.swing.JPanel;

import main.Window;

/**
 * LoadBackground Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class LoadBackground extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<LoadImage> images, images_backup;
	private ArrayList<Integer> item_i, item_j;
	private int x_length, y_length, width, load;
	private double[][] data;

	/**
	 * Konstruktor - Erstellt den Hintergrund und setzt Standards
	 * 
	 * @param window Fensterobjekt
	 * @param data Hintergrunddaten als double[][] hinterlegt
	 */
	public LoadBackground(Window window, double[][] data) {
		this.images_backup = new ArrayList<LoadImage>();
		this.images = new ArrayList<LoadImage>();
		this.item_i = new ArrayList<Integer>();
		this.item_j = new ArrayList<Integer>();
		this.x_length = data[0].length;
		this.y_length = data.length;
		this.width = 64;
		this.load = 16;
		this.data = data;
		this.setLayout(null);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.0) {
					images.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
							(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
				} else if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.1) {
					images_backup.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
							(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
					images_backup.get(images_backup.size() - 1).setVisible(false);
					images.add(new LoadImage(j * width, i * width, width, 4 * load, 6 * load, load, "sprite.png", this,
							false));
					item_i.add(i);
					item_j.add(j);
				}else if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.2) {
					images_backup.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
							(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
					images_backup.get(images_backup.size() - 1).setVisible(false);
					images.add(new LoadImage(j * width, i * width, width, 3 * load, 6 * load, load, "sprite.png", this,
							false));
					item_i.add(i);
					item_j.add(j);
				}else if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.7) {
					images_backup.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
							(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
					images_backup.get(images_backup.size() - 1).setVisible(false);
					images.add(new LoadImage(j * width, i * width, width, 0 * load, 6 * load, load, "sprite.png", this,
							false));
					item_i.add(i);
					item_j.add(j);
				}else if (Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 == 0.9) {
					images_backup.add(new LoadImage(j * width, i * width, width, (int) data[i][j] % 8 * load,
							(int) Math.floor(data[i][j] / 8) * load, load, "sprite.png", this, false));
					images_backup.get(images_backup.size() - 1).setVisible(false);
					images.add(new LoadImage(j * width, i * width, width, 1 * load, 6 * load, load, "sprite.png", this,
							false));
					item_i.add(i);
					item_j.add(j);
				}
				if(Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10 != 0.0) {
					System.out.println(Math.floor((data[i][j] - Math.floor(data[i][j])) * 10) / 10);
				}

			}
		}
		this.setSize(this.x_length * this.width, this.y_length * this.width);
		this.setLocation(0, 0);
		this.setVisible(true);
		window.add(this);
	}

	/**
	 * resetScaling - Methode, UNUSED -> Ansatz für nicht priorisierte Story... -> TODO
	 */
	public void resetScaling() {
		for (int i = 0; i < y_length; i++) {
			for (int j = 0; j < x_length; j++) {
				images.get(i * j); // TODO
			}
		}
	}

	/**
	 * resetPosition Methode verschiebt den Hintergrund
	 * 
	 * @param x_dir Wert, um den der Hintergrund in X-Richtung verschoben werden soll
	 * @param y_dir Wert, um den der Hintergrund in Y-Richtung verschoben werden soll
	 */
	public void resetPosition(int x_dir, int y_dir) {
		this.setLocation(this.getLocation().x + x_dir, this.getLocation().y + y_dir);
	}
	
	/**
	 * setPosition Methode verschiebt den Hintergrund zu einer bestimmten Position
	 * 
	 * @param x_dir Wert, auf den der Hintergrund in X-Position verschoben werden soll
	 * @param y_dir Wert, auf den der Hintergrund in Y-Position verschoben werden soll
	 */
	public void setPosition(int x_dir, int y_dir) {
		this.setLocation(x_dir, y_dir);
	}

	/**
	 * @return Breite des Hintergrundes
	 */
	public int getXLength() {
		return x_length;
	}

	/**
	 * @return Höhe des Hintergrundes
	 */
	public int getYLength() {
		return y_length;
	}

	/**
	 * @return Breite/Höhe einer einzelnen Hintergrundeinheit
	 */
	public int getScale() {
		return width;
	}

	/**
	 * @param y Reihe der Hintergrundeinheit
	 * @param x Spalte der Hintergrundeinheit
	 * @return Hintergrundeinheit bei (X | Y)
	 */
	public double getData(int y, int x) {
		return data[y][x];
	}

	/**
	 * @param count nimmt das Backup-Bild und fügt es in den normalen Hintergrund ein -> BSP: Stein aufheben
	 */
	public void resetBackground(int count) {
		images.set(item_i.get(count) * item_j.get(count), null);
		images.set(item_i.get(count) * item_j.get(count), images_backup.get(count));
	}
	
	/**
	 * @param b setzt das Bestimmte Backup-Bild auf Visible
	 */
	public void setBackupTexture(int b){
		images_backup.get(b).setVisible(true);
	}
}
