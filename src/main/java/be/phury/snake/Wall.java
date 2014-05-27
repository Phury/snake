package be.phury.snake;

import static be.phury.snake.SnakeGame.TILE_SIZE;

import java.awt.Graphics2D;

import be.phury.j2d.framwork.Colors;

public class Wall extends AbstractSprite {
	public Wall(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Colors.fromRGB("#0000ff"));
		g.drawRect(x*TILE_SIZE, y*TILE_SIZE, w*TILE_SIZE, h*TILE_SIZE);
	}
	
	@Override
	public boolean isWall() {
		return true;
	}
}