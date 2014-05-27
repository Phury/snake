package be.phury.snake;

import static be.phury.snake.SnakeGame.TILE_SIZE;

import java.awt.Graphics2D;

import be.phury.j2d.framwork.Colors;

public class Snake extends AbstractSprite {

	private Action action;
	
	public Snake(int x, int y) {
		super(x, y);
		action = Action.RIGHT;
	}

	@Override
	public void update(double dt) {
		if (getAction() == Action.LEFT) {
			x-=w;
		} else if (getAction() == Action.RIGHT) {
			x+=w;
		} else if (getAction() == Action.UP) {
			y-=h;
		} else if (getAction() == Action.DOWN) {
			y+=h;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Colors.fromRGB("#ffa500"));
		g.fillRect(getX()*TILE_SIZE, getY()*TILE_SIZE, getW()*TILE_SIZE-1, getH()*TILE_SIZE-1);
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action direction) {
		if (direction != this.action) this.action = direction;
	}
}