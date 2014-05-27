package be.phury.snake;

import static be.phury.snake.SnakeGame.TILE_SIZE;

import java.awt.Graphics2D;

import be.phury.j2d.framwork.Colors;

public class SnakeBody extends AbstractSprite {
	public SnakeBody(int x, int y, int ttl) {
		super(x, y, ttl);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Colors.fromRGB("#ffa500"));
		g.fillRect(x*TILE_SIZE, y*TILE_SIZE, w*TILE_SIZE-1, h*TILE_SIZE-1);
//		g.drawRect(x*TILE_SIZE, y*TILE_SIZE, w*TILE_SIZE, h*TILE_SIZE);
	}
	
	@Override
	public boolean isSnakeBody() {
		return true;
	}
}