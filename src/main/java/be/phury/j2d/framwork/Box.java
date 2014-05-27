package be.phury.j2d.framwork;


public class Box {

	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected double speed;
	protected boolean alive;
		
	public Box(int x, int y, int w, int h) {
		this(x, y, w, h, 0.0);
	}
	
	public Box(int x, int y, int w, int h, double speed) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.alive = true;
	}
	
	public boolean intersect(Box t) {
		return x+w >= t.x+t.w &&
               x <= t.x &&
               y+h >=t.y+t.h &&
               y <= t.y;     
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}
	
}