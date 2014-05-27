package be.phury.j2d.framwork;

public class Random {

	public static int inRange(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}
