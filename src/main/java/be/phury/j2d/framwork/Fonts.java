package be.phury.j2d.framwork;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.IOException;

import be.phury.snake.SnakeGame;

public class Fonts {

	public static Font getGameFont(String name) {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, SnakeGame.class.getClassLoader().getResourceAsStream(name));
			return f.deriveFont(12f);
		} catch (FontFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void drawCenterdString(Graphics2D g, Font font, String txt, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(font);
		int height = metrics.getHeight()/2;
		int width = metrics.stringWidth(txt)/2;
		g.drawString(txt, x-width, y-height);
	}
}
