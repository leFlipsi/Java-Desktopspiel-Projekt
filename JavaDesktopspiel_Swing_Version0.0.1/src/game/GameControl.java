package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import image.LoadBackground;
import main.Window;

public class GameControl extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean active, way_h_free, way_v_free;
	private String id, charaktername, spielstandname, item_status;
	private int player_pos_x, player_pos_y, player_rotation, width, running;
	private Window window;
	private Charakter[] charakter;
	private LoadBackground bg;
	private int[] walking, lastActive;
	private Thread game;

	public GameControl(Window window) {
		Dimension dim = new Dimension(window.getWidth(), window.getHeight());
		this.walking = new int[4];
		this.walking[0] = 0;
		this.walking[1] = 0;
		this.walking[2] = 0;
		this.walking[3] = 0;
		this.lastActive = new int[2];
		this.lastActive[0] = 0;
		this.lastActive[1] = 0;
		this.running = 1;
		this.setPreferredSize(dim);
		this.active = false;
		this.window = window;
		this.width = 64;
		charakter = new Charakter[4];
		charakter[0] = new Charakter(window, 0, 0);
		charakter[0].setVisible(false);
		charakter[1] = new Charakter(window, 16, 0);
		charakter[1].setVisible(false);
		charakter[2] = new Charakter(window, 32, 0);
		charakter[2].setVisible(false);
		charakter[3] = new Charakter(window, 48, 0);
		charakter[3].setVisible(false);
	}

	public void setActive(String id, String charaktername, String spielstandname, String item_status, int player_x,
			int player_y, int player_rotation, LoadBackground bg) {
		this.bg = bg;
		this.id = id;
		this.charaktername = charaktername;
		this.spielstandname = spielstandname;
		this.item_status = item_status;
		this.player_pos_x = player_x;
		this.player_pos_y = player_y;
		this.player_rotation = player_rotation;
		this.charakter[0].setVisible(true);
		this.addKeyListener(this);
		this.setFocusable(true);
		bg.resetPosition(-32, 0);
	}

	public synchronized void start() {
		this.active = true;
		game = new Thread(this, "Game");
		game.start();
	}

	public synchronized void stop() {
		this.active = false;
		try {
			game.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while (active) {
			update();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				render();
				delta--;
			}
		}
	}

	private void render() {
		/*
		 * BufferStrategy bs = getBufferStrategy(); if (bs == null) {
		 * createBufferStrategy(3); return; } Graphics g = bs.getDrawGraphics();
		 * // Graphic changes g.setColor(Color.BLUE); g.fillRect(0, 0, 200,
		 * 200); // g.dispose(); bs.show();
		 */
		if (way_h_free && way_v_free) {
			bg.resetPosition(running * (walking[2] - walking[3]), running * (walking[0] - walking[1]));
			finishStep();
		} else if (way_v_free) {
			bg.resetPosition(0, running * (walking[0] - walking[1]));
			finishStep();
		} else if (way_h_free) {
			bg.resetPosition(running * (walking[2] - walking[3]), 0);
			finishStep();
		} else {

		}
	}

	public void finishStep() {
		if (-1 * bg.getLocation().x % 64 != 32) {
			if (lastActive[1] == 1) {
				bg.resetPosition((lastActive[1] - walking[3]), 0);
			} else if (lastActive[1] == -1) {
				bg.resetPosition((walking[2] + lastActive[1]), 0);
			}
		}
		if (bg.getLocation().y % 64 != 0) {
			if (lastActive[0] == 1) {
				bg.resetPosition(0, (lastActive[0] - walking[1]));
			} else if (lastActive[0] == -1) {
				bg.resetPosition(0, (walking[0] + lastActive[0]));
			}
		}
	}

	public void walkAnimationH() {
		int x = bg.getLocation().x;
		if (0 <= -1*((x+32) % 64) && -1*((x+32) % 64) <= 15) {
			charakter[0].setVisible(true);
			charakter[3].setVisible(false);
			charakter[1].setVisible(false);
		} else if (16 <= -1*((x+32) % 64) && -1*((x+32) % 64) <= 31) {
			charakter[1].setVisible(true);
			charakter[0].setVisible(false);
			charakter[2].setVisible(false);
		} else if (32 <= -1*((x+32) % 64) && -1*((x+32) % 64) <= 47) {
			charakter[2].setVisible(true);
			charakter[1].setVisible(false);
			charakter[3].setVisible(false);
		} else if (48 <= -1*((x+32) % 64) && -1*((x+32) % 64) <= 64) {
			charakter[3].setVisible(true);
			charakter[2].setVisible(false);
			charakter[0].setVisible(false);
		}
	}

	private void update() {
		walkAnimationH();
		if ((bg.getLocation().x >= -32 && walking[2] > 0)
				|| (bg.getLocation().x <= -1 * (bg.getWidth() - window.getWidth() - charakter[0].getWidth() / 2)
						&& walking[3] > 0)) {
			way_h_free = false;
		} else {
			way_h_free = true;
		}
		if ((bg.getLocation().y >= 0 && walking[0] > 0)
				|| (bg.getLocation().y <= -1 * (bg.getHeight() - window.getHeight()) && walking[1] > 0)) {
			way_v_free = false;
		} else {
			way_v_free = true;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			walking[0] = 1;
			lastActive[0] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			walking[1] = 1;
			lastActive[0] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			walking[2] = 1;
			lastActive[1] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			walking[3] = 1;
			lastActive[1] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			running = 10;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			walking[0] = 0;
			lastActive[0] = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			walking[1] = 0;
			lastActive[0] = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			walking[2] = 0;
			lastActive[1] = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			walking[3] = 0;
			lastActive[1] = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			running = 1;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
