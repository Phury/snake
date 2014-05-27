package be.phury.j2d.framwork;

import java.awt.Graphics2D;

public abstract class Sprite extends Box {
	
	public Sprite(Box b) {
		this(b.getX(), b.getY(), b.getW(), b.getH());
	}
	
	public Sprite(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public abstract void update(double dt);
	public abstract void render(Graphics2D g);
}
