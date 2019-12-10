package neon.graphics.animation;

import java.util.ArrayList;

import neon.graphics.Sprite;

public class Animation {

	private ArrayList<Sprite> sprites;
	private float frequency;
	private boolean looping;
	private boolean isDone = false;
	private int currentSprite = 0;
	
	public Animation(float frequency, boolean looping) {
		this.frequency = frequency;
		this.looping = looping;
		this.sprites = new ArrayList<Sprite>();
	}
	
	public ArrayList<Sprite> getSprites() {
		return this.sprites;
	}
	
	public float getFrequency() {
		return this.frequency;
	}
	
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	
	public boolean isLooping() {
		return this.looping;
	}
	
	public void setLooping(boolean looping) {
		this.looping = looping;
	}
	
	public int getCurrentSpriteIndex() {
		return this.currentSprite;
	}
	
	public void setCurrentSpriteIndex(int currentSprite) {
		this.currentSprite = currentSprite;
	}
	
	public Sprite getCurrentSprite() {
		return sprites.get(currentSprite);
	}
	
	public void nextSprite() {
		currentSprite++;
		if (currentSprite >= sprites.size()) {
			if (!looping) {
				currentSprite = sprites.size() - 1;
				isDone = true;
			} else {
				currentSprite = 0;
			}
		}
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}
