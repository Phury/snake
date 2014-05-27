package be.phury.j2d.framwork;

public class GameConfig {

	private Integer screenWidth;
	private Integer screenHeight;
	
	public Integer screenWidth() {
		return screenWidth;
	}
	public GameConfig screenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
		return this;
	}
	public Integer screenHeight() {
		return screenHeight;
	}
	public GameConfig screenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
		return this;
	}
	
}
