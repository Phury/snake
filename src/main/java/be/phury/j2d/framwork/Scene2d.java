package be.phury.j2d.framwork;

import java.awt.Graphics2D;

public interface Scene2d {

	public abstract void setup();

	public abstract void udpate(double dt);

	public abstract void render(Graphics2D g);

	public abstract void keyPressed(int keyCode);

	public abstract void keyReleased(int keyCode);

}