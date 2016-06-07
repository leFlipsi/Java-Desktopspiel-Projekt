package image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LoadImage extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private int show_x, show_y, show_width, load_x, load_y, load_width;
	private String resource;
	private BufferedImage img2;
	private JPanel panel;
	private boolean is_charakter;

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

	public void resetScaling() {

	}
}
