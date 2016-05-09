package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import konstanten.TextVars;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private int window_width = 1024;
	private int window_height = 576;
	private Menu menu;

	public Window(String title) {
		this.setResizable(false);
		this.setTitle(title);
		this.updateWindowSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		menu = new Menu(this, "start");
	}

	public void resetWindowScale(int w, int h) {
		this.window_width = w;
		this.window_height = h;
	}

	public void updateWindowSize() {
		this.setSize(window_width, window_height);
	}

	public void keepWindowSize() {
		this.window_width = this.getWidth();
		this.window_height = this.getHeight();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window window = new Window(TextVars.window_title);
			}
		});
	}
}
