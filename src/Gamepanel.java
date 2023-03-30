import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400, HEIGHT = 400;

	private Thread thread;
	private boolean running = false;

	private BodyPart b;
	private ArrayList<BodyPart> snake;

	private Apple apple;
	private ArrayList<Apple> apples;

	private Random r;

	private int xCoor = 10, yCoor = 10, size = 15;

	private boolean right = true, left = false, up = false, down = false;

	private int ticks = 0;

	public Gamepanel() {
		setFocusable(true);

		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		snake = new ArrayList<BodyPart>();
		apples = new ArrayList<Apple>();

		r = new Random();

		start();
	}

	public void tick() {
		if (snake.size() == 0) {
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
		}
		if (apples.size() == 0) {
			int xCoor = r.nextInt(39);
			int yCoor = r.nextInt(39);

			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
		}

		for (int i = 0; i < apples.size(); i++) {
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i++;
			}
		}

		for (int i = 0; i < snake.size(); i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if (i != snake.size() - 1) {
					stop();
				}
			}
		}
		if (xCoor < 0 || xCoor > 39 || yCoor < 0 || yCoor > 39) {
			stop();
		}

		ticks++;

		if (ticks > 260000) {
			if (right)
				xCoor++;
			if (left)
				xCoor--;
			if (up)
				yCoor--;
			if (down)
				yCoor++;

			ticks = 0;

			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);

			if (snake.size() > size) {
				snake.remove(0);
			}
		}

		if (apples.size() == 0) {
			int xCoor = r.nextInt(49);
			int yCoor = r.nextInt(49);

			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
		}

		for (int i = 0; i < apples.size(); i++) {
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i++;
			}

		}
// colis�o com o corpo da cobrinha
		
		for(int i = 0; i < snake.size(); i++ ) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != snake.size()-1) {
					System.out.println("GAME OVER");
					stop();
				}
			}
		}
// colis�o com a borda
		if (xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
			System.out.println("GAME OVER");
			stop();
		}

	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < WIDTH / 10; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		for (int i = 0; i < HEIGHT / 10; i++) {
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		}

		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}

	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT && !left) {
			up = false;
			down = false;
			right = true;
		}
		if (key == KeyEvent.VK_LEFT && !right) {
			up = false;
			down = false;
			left = true;
		}
		if (key == KeyEvent.VK_UP && !down) {
			left = false;
			right = false;
			up = true;
		}
		if (key == KeyEvent.VK_DOWN && !up) {
			left = false;
			right = false;
			down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}