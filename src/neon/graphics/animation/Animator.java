package neon.graphics.animation;

import java.util.HashMap;

import neon.graphics.Sprite;

public class Animator {
	
	private HashMap<String, Animation> animations;
	private Animation currentAnimation;
	private String state;
	private int timeEllapsed = 0;
	
	public Animator() {
		animations = new HashMap<String, Animation>();
	}
	
	public void setState(String state) {
		this.state = state;
		this.currentAnimation = animations.get(state);
		this.currentAnimation.setCurrentSpriteIndex(0);
	}
	
	public String getState() {
		return this.state;
	}
	
	public void addAnimation(Animation animation, String state) {
		animations.put(state, animation);
	}
	
	public void updateAnimations(int delta) {
		timeEllapsed += delta;
		if (timeEllapsed >= currentAnimation.getFrequency()) {
			currentAnimation.nextSprite();
			timeEllapsed = 0;
		}
	}
	
	public Sprite getCurrentSprite() {
		return currentAnimation.getCurrentSprite();
	}
}
