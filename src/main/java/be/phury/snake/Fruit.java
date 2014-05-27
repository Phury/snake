package be.phury.snake;

import static be.phury.snake.SnakeGame.TILE_SIZE;

import java.awt.Graphics2D;

import be.phury.j2d.framwork.Colors;

public class Fruit extends AbstractSprite {
	public Fruit(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Colors.fromRGB("#00ff00"));
		g.fillRect(x*TILE_SIZE, y*TILE_SIZE, w*TILE_SIZE-1, h*TILE_SIZE-1);
	}
	
	@Override
	public boolean isFruit() {
		return true;
	}
}