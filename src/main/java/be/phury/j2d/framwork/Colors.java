package be.phury.j2d.framwork;

import java.awt.Color;

public class Colors {

	public static Color fromRGB(String hexStr) {
		String hex = hexStr;
		if (hexStr.startsWith("#")) hex = hexStr.substring(1);
		return new Color(
				Integer.parseInt(hex.substring(0, 2), 16),
				Integer.parseInt(hex.substring(2, 4), 16),
				Integer.parseInt(hex.substring(4, 6), 16));
	}
}
