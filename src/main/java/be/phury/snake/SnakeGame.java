package be.phury.snake;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import be.phury.j2d.framwork.Fonts;
import be.phury.j2d.framwork.Game2d;
import be.phury.j2d.framwork.GameConfig;
import be.phury.j2d.framwork.Random;
import be.phury.j2d.framwork.Scene2d;
import be.phury.j2d.framwork.Sprites;

public class SnakeGame implements Scene2d {

	public static final Integer TILE_SIZE = 8;
	public static final Integer SCREEN_WIDTH = 256;
	public static final Integer SCREEN_HEIGHT = 240;
	private static Font FONT = Fonts.getGameFont("Minecraftia.ttf");
	
	private double totalTime;
	
	private double speed;
	private boolean paused;
	private int score;
	private int length;
	private Snake snake;
	private Sprites<AbstractSprite> tiles = new Sprites<>();
	
	@Override
	public void setup() {
		paused = false;
		score = 0;
		length = 3;
		speed = 0.25;
		snake = new Snake(4, 8);
		tiles.clear();
		tiles.add(snakeBody(3, 8, length-1));
		tiles.add(snakeBody(2, 8, length-2));
		tiles.add(new Fruit(8, 8));
		tiles.addAll(walls());
	}
	
	private int getW() {
		return (SCREEN_WIDTH/TILE_SIZE);
	}
	
	private int getH() {
		return (SCREEN_HEIGHT/TILE_SIZE);
	}
	
	private SnakeBody snakeBody(int x, int y, int ttl) {
		return new SnakeBody(x, y, ttl);
	}
	
	private Fruit randomFruit() {
		int randX = Random.inRange(1, getW()-2);
		int randY = Random.inRange(1, getH()-2);
		System.out.println("fruit popped at: " + randX + "-" + randY);
		return new Fruit(randX, randY);
	}
	
	private Sprites<Wall> walls() {
		Sprites<Wall> walls = new Sprites<>();
		for (int i = 0; i < getW(); i++) {
			for (int j = 0; j < getH(); j++) {
				if (i == 0 || j == 0 || i == getW()-1 || j == getH()-1) {
					walls.add(new Wall(i, j));
				}
			}
		}
		return walls;
	}

	@Override
	public void udpate(double dt) {
		if (paused) return;
		
		totalTime += dt;
		if (totalTime >= speed) {
			totalTime = 0;
			
			int oldX = snake.getX();
			int oldY = snake.getY();
			snake.update(dt);
			tiles.updateAll(dt);
			tiles.add(snakeBody(oldX, oldY, length-1));
			
			checkCollisions();
			removeDeadTiles();
		}
	}

	private void checkCollisions() {
		final Sprites<AbstractSprite> toAdd = new Sprites<>();
		for (AbstractSprite t : tiles) {
			if (t.intersect(snake)) {
				if (t.isSnakeBody()) {
					snake.setAlive(false);
				} else if (t.isFruit()) {
					t.setAlive(false);
					toAdd.add(randomFruit());
					increaseSnakeLength();
					score+=100;
				} else if (t.isWall()) {
					snake.setAlive(false);
				}
			}
		}
		if (snake.getX() > getW()) snake.setX(0);
		if (snake.getY() > getH()) snake.setY(0);
		if (snake.getX() < 0) snake.setX(getW());
		if (snake.getY() < 0) snake.setY(getH());
		tiles.addAll(toAdd);
	}
	
	private void increaseSnakeLength() {
		for (AbstractSprite t : tiles) {
			if (t.isSnakeBody()) t.increaseTtl();
		}
		length += 1;
	}

	private void removeDeadTiles() {
		final Sprites<AbstractSprite> toRemove = new Sprites<>(); 
		for (AbstractSprite t : tiles) {
			if (!t.isAlive()) toRemove.add(t);
			if (!snake.isAlive() && t.isSnakeBody()) toRemove.add(t);
		}
		tiles.removeAll(toRemove);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setFont(FONT);
		tiles.renderAll(g);
		if (snake.isAlive()) {
			snake.render(g);
			g.drawString("score: " + score, 8, 8);
			if (paused) {
				drawCenterdString(g, "PAUSE");
			}
		} else {
			drawCenterdString(g, "GAME OVER");
		}
	}
	
	private void drawCenterdString(Graphics2D g, String txt) {
		Fonts.drawCenterdString(g, FONT, txt, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void keyPressed(int keyCode) {
		if (KeyEvent.VK_UP == keyCode) snake.setAction(Action.UP); 
		else if (KeyEvent.VK_DOWN == keyCode) snake.setAction(Action.DOWN); 
		else if (KeyEvent.VK_LEFT == keyCode) snake.setAction(Action.LEFT); 
		else if (KeyEvent.VK_RIGHT == keyCode) snake.setAction(Action.RIGHT);
		else if (KeyEvent.VK_R == keyCode) setup();
		else if (KeyEvent.VK_P == keyCode) paused = !paused;
 	}

	@Override
	public void keyReleased(int keyCode) {
	}
	
	public static void main(String[] args) {
		Game2d.createAndDisplay(new SnakeGame(), new GameConfig().screenWidth(SCREEN_WIDTH).screenHeight(SCREEN_HEIGHT));
	}
}
