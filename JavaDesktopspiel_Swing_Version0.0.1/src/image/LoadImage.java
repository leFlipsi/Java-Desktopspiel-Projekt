package image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Window;

public class LoadImage extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private int show_x, show_y, show_width, load_x, load_y, load_width;
	private String resource;

	public LoadImage(int sx, int sy, int sw, int lx, int ly, int lw, String res, Window window) {
		this.show_x = sx;
		this.show_y = sy;
		this.show_width = sw;
		this.load_x = lx;
		this.load_y = ly;
		this.load_width = lw;
		this.resource = res;
		
		this.setLayout(null);
		this.setLocation(this.show_x, this.show_y);
		this.setSize(this.show_width, this.show_width);
		this.setOpaque(true);

		try {
			img = ImageIO.read(LoadImage.class.getResourceAsStream(this.resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		window.add(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage img2 = img.getSubimage(this.load_x, this.load_y, this.load_width, this.load_width);
		g.drawImage(img2, 0, 0, this.show_width, this.show_width, this);
	}
}
