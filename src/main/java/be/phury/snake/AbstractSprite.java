package be.phury.snake;

import be.phury.j2d.framwork.Box;
import be.phury.j2d.framwork.TimedSprite;

public abstract class AbstractSprite extends TimedSprite {
	public AbstractSprite(int x, int y, int ttl) {
		super(new Box(x, y, 1, 1), ttl);
	}
	public AbstractSprite(int x, int y) {
		this(x, y, -1);
	}

	public boolean isFruit() {
		return false;
	}

	public boolean isSnakeBody() {
		return false;
	}
	
	public boolean isWall() {
		return false;
	}
}