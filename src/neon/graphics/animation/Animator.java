package neon.graphics.animation;

import java.util.HashMap;

import neon.graphics.Sprite;
import neon.time.TimeInfo;

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
		//System.out.println(this.currentAnimation.getCurrentSprite().getName());
		this.currentAnimation.setCurrentSpriteIndex(0);
	}
	
	public String getState() {
		return this.state;
	}
	
	public boolean hasState(String state) {
		if (animations.get(state) == null) {
			return false;
		}
		return true;
	}
	
	public void addAnimation(Animation animation, String state) {
		animations.put(state, animation);
	}
	
	public void updateAnimations() {
		timeEllapsed += TimeInfo.getDelta();
		if (timeEllapsed >= currentAnimation.getFrequency()) {
			currentAnimation.nextSprite();
			timeEllapsed = 0;
		}
	}
	
	public Sprite getCurrentSprite() {
		//System.out.println(currentAnimation.getCurrentSprite().getName());
		return currentAnimation.getCurrentSprite();
	}
}
