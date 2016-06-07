package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import image.LoadBackground;
import image.LoadImage;
import konstanten.TextVars;
import main.Window;

public class GameControl extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean active, way_h_free, way_v_free, e_pressed;
	private String id, charaktername, spielstandname, item_status;
	private int player_pos_x, player_pos_y, player_rotation, width, running, bg_border_x, bg_border_y, locx, locy;
	private double bg_unwalkable, xplus1, xminus1, yplus1, yminus1;
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
		this.bg_border_x = 8;
		this.bg_border_y = 4;
		this.bg_unwalkable = 32.0;
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
		window.setTitle(TextVars.window_title + " | Charakter: " + charaktername + " | Spielstand: " + spielstandname);
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
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;

		int updates = 0;
		int renders = 0;

		while (active) {
			update();
			updates++;
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				render();
				renders++;
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.setTitle(TextVars.window_title + " | Charakter: " + charaktername + " | Spielstand: "
						+ spielstandname + " | FPS: " + renders + " | UPS: " + updates);
				renders = 0;
				updates = 0;
			}
		}
	}

	private void render() {
		locx = bg.getLocation().x;
		locy = bg.getLocation().y;
		nextStepBackground();
		walkingXCheckY();
		walkingYCheckX();
		freeWay();
		if (!e_pressed) {
			if (way_h_free && way_v_free) {
				if (walking[2] - walking[3] != 0 && walking[0] - walking[1] != 0 && (bg.getLocation().x - 32) % 64 == 0
						&& (bg.getLocation().y % 64) == 0) {
					bg.resetPosition(running * (walking[2] - walking[3]), 0);
				} else {
					bg.resetPosition(running * (walking[2] - walking[3]), running * (walking[0] - walking[1]));
				}
			} else if (way_v_free) {
				bg.resetPosition(0, running * (walking[0] - walking[1]));
			} else if (way_h_free) {
				bg.resetPosition(running * (walking[2] - walking[3]), 0);
			}
		}
		finishStep();
	}

	private void update() {
		walkAnimationH();
		locx = bg.getLocation().x;
		locy = bg.getLocation().y;
		nextStepBackground();
		walkingXCheckY();
		walkingYCheckX();
		freeWay();
	}

	public void nextStepBackground() {
		xplus1 = bg.getData(bg_border_y + player_pos_y, bg_border_x + player_pos_x + 1);
		xminus1 = bg.getData(bg_border_y + player_pos_y, bg_border_x + player_pos_x - 1);
		yplus1 = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x);
		yminus1 = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x);
	}

	public void walkingXCheckY() {
		if (locx % 64 == -32) {
			player_pos_x = -1 * (locx + 32) / 64;
		} else {
			if (-1 * (locx - 32) / 64 > player_pos_x) {
				if (yplus1 < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1)) {
					yplus1 = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1);
				}
				if (yminus1 < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1)) {
					yminus1 = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1);
				}
			} else if (-1 * (locx + 32) / 64 < player_pos_x) {
				if (yplus1 < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1)) {
					yplus1 = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1);
				}
				if (yminus1 < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1)) {
					yminus1 = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1);
				}
			}
		}
	}

	public void walkingYCheckX() {
		if (locy % 64 == 0) {
			player_pos_y = -1 * locy / 64;
		} else {
			if (-1 * locy / 64 < player_pos_y) {
				if (xplus1 < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1)) {
					xplus1 = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1);
				}
				if (xminus1 < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1)) {
					xminus1 = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1);
				}
			} else if (-1 * locy / 64 + 1 > player_pos_y) {
				if (xplus1 < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1)) {
					xplus1 = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1);
				}
				if (xminus1 < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1)) {
					xminus1 = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1);
				}

			}
		}
	}

	public void freeWay() {
		if (((locx >= -32 || xminus1 >= bg_unwalkable) && walking[2] > 0)
				|| ((locx <= -1 * (bg.getWidth() - window.getWidth() - charakter[0].getWidth() / 2)
						|| xplus1 >= bg_unwalkable) && walking[3] > 0)) {
			way_h_free = false;
		} else {
			way_h_free = true;
		}
		if (((locy >= 0 || yminus1 >= bg_unwalkable) && walking[0] > 0)
				|| ((locy <= -1 * (bg.getHeight() - window.getHeight()) || yplus1 >= bg_unwalkable)
						&& walking[1] > 0)) {
			way_v_free = false;
		} else {
			way_v_free = true;
		}
	}

	public void finishStep() {
		if (-1 * bg.getLocation().x % 64 != 32 && walking[2] - walking[3] == 0) {
			if (lastActive[1] == 1) {
				bg.resetPosition((lastActive[1] - walking[3]), 0);
			} else if (lastActive[1] == -1) {
				bg.resetPosition((walking[2] + lastActive[1]), 0);
			}
		}
		if (bg.getLocation().y % 64 != 0 && walking[0] - walking[1] == 0) {
			if (lastActive[0] == 1) {
				bg.resetPosition(0, (lastActive[0] - walking[1]));
			} else if (lastActive[0] == -1) {
				bg.resetPosition(0, (walking[0] + lastActive[0]));
			}
		}
	}

	public void walkAnimationH() {
		int x = bg.getLocation().x;
		if (0 <= -1 * ((x + 32) % 64) && -1 * ((x + 32) % 64) <= 15) {
			charakter[0].setVisible(true);
			charakter[3].setVisible(false);
			charakter[1].setVisible(false);
		} else if (16 <= -1 * ((x + 32) % 64) && -1 * ((x + 32) % 64) <= 31) {
			charakter[1].setVisible(true);
			charakter[0].setVisible(false);
			charakter[2].setVisible(false);
		} else if (32 <= -1 * ((x + 32) % 64) && -1 * ((x + 32) % 64) <= 47) {
			charakter[2].setVisible(true);
			charakter[1].setVisible(false);
			charakter[3].setVisible(false);
		} else if (48 <= -1 * ((x + 32) % 64) && -1 * ((x + 32) % 64) <= 64) {
			charakter[3].setVisible(true);
			charakter[2].setVisible(false);
			charakter[0].setVisible(false);
		}
	}

	public void pickUpItem() {
		if (this.player_rotation == 0 && yplus1 == 10.0) { // Double für
															// Textureinheit mit
															// Item

		} else if (this.player_rotation == 1 && xplus1 == 10.0) { // Double für
																	// Textureinheit
																	// mit Item

		} else if (this.player_rotation == 2 && yminus1 == 10.0) { // Double für
																	// Textureinheit
																	// mit Item

		} else if (this.player_rotation == 3 && xminus1 == 10.0) { // Double für
																	// Textureinheit
																	// mit Item

		}
		bg.resetBackground(0);
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
			running = 3;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			e_pressed = true;
			pickUpItem();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			walking[0] = 0;
			lastActive[0] = 1;
			this.player_rotation = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			walking[1] = 0;
			lastActive[0] = -1;
			this.player_rotation = 2;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			walking[2] = 0;
			lastActive[1] = 1;
			this.player_rotation = 3;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			walking[3] = 0;
			lastActive[1] = -1;
			this.player_rotation = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			running = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			e_pressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
