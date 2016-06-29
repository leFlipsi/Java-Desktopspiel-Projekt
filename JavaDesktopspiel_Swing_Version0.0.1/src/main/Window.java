package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import konstanten.TextVars;

/**
 * Window Klasse
 * 
 * @author Philipp R�hlicke, Tim Ziegelbauer, Cedric R�hr
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Window extends JFrame {
	private int window_width = 1024;
	private int window_height = 576;
	private Menu menu;

	/**
	 * Konstruktor - Erstellt das Fenster
	 * 
	 * @param title Fenstertitel
	 */
	public Window(String title) {
		this.setResizable(false);
		this.setTitle(title);
		this.updateWindowSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		setMenu(new Menu(this, "start"));
	}

	/**
	 * �ndert die Werte f�r die Breite und H�he -> NICHT die Breite und H�he selbst
	 * 
	 * @param w Breite
	 * @param h H�he
	 */
	public void resetWindowScale(int w, int h) {
		this.window_width = w;
		this.window_height = h;
	}

	/**
	 * Nimmt die Werte f�r die Breite und H�he und wendet sie an!
	 */
	public void updateWindowSize() {
		this.setSize(window_width, window_height);
	}

	/**
	 * Setzt die Werte f�r die Breite und H�he auf die Momentanen Abmessungen zur�ck
	 */
	public void keepWindowSize() {
		this.window_width = this.getWidth();
		this.window_height = this.getHeight();
	}

	/**
	 * Startmethode -> RUN
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window window = new Window(TextVars.window_title);
			}
		});
	}

	/**
	 * @return Menu-Objekt
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu legt das Men�-Objekt fest
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
