package image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * LoadImage Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class LoadImage extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private int show_x, show_y, show_width, load_x, load_y, load_width;
	private String resource;
	private BufferedImage img2;
	private JPanel panel;
	private boolean is_charakter;

	/**
	 * @param sx X-Position des Bildes im Fenster
	 * @param sy Y-Position des Bildes im Fenster
	 * @param sw Breite des Bildes im Fenster
	 * @param lx X-Position des Bildes in der Sprite
	 * @param ly Y-Position des Bildes in der Sprite
	 * @param lw Breite des Bildes in der Sprite
	 * @param res Name der Sprite-Datei
	 * @param panel Panel, auf welchem das Bild hinterlegt wird
	 * @param is_charakter Ist das Bild ein Charakter? (Oder Inventar/Item)
	 */
	public LoadImage(int sx, int sy, int sw, int lx, int ly, int lw, String res, JPanel panel, boolean is_charakter) {
		this.show_x = sx;
		this.show_y = sy;
		this.show_width = sw;
		this.load_x = lx;
		this.load_y = ly;
		this.load_width = lw;
		this.resource = res;
		this.panel = panel;
		this.is_charakter = is_charakter;
		try {
			img = ImageIO.read(LoadImage.class.getResourceAsStream(this.resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setOpaque(false);
		panelSettings();
	}

	/**
	 * panelSettings - Methode legt die Einstellungen für das Panel des Bildes fest
	 */
	public void panelSettings() {
		this.setLayout(null);
		this.setLocation(this.show_x, this.show_y);
		this.setSize(this.show_width, this.show_width);
		this.setVisible(true);
		try {
			panel.add(this);
		} catch (Exception e) {

		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try{	
			if (is_charakter) {
				img2 = img.getSubimage(this.load_x, this.load_y, this.load_width, this.load_width);
				g.drawImage(img2, 0, 0, panel.getWidth(), panel.getHeight(), this);
			} else {
				img2 = img.getSubimage(this.load_x, this.load_y, this.load_width, this.load_width);
				g.drawImage(img2, 0, 0, this.show_width, this.show_width, this);
			}
		}catch(RasterFormatException rfe){
			
		}
	}

	/**
	 * TODO -> siehe LoadBackground
	 */
	public void resetScaling() {

	}
}
