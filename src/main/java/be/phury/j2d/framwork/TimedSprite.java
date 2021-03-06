package be.phury.j2d.framwork;


public abstract class TimedSprite extends Sprite {
	private int ttl;
	
	public TimedSprite(Box b, int ttl) {
		super(b);
		this.ttl = ttl;
	}
	
	public void increaseTtl() {
		ttl++;
	}

	@Override
	public void update(double dt) {
		ttl--;
		if (ttl == 0) alive = false;
	}
}