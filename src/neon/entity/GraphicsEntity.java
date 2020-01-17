package neon.entity;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import neon.graphics.EntityGraphics;
import neon.graphics.Sprite;
import neon.graphics.animation.Animation;
import neon.graphics.animation.Animator;
import neon.io.SpriteLoader;

public class GraphicsEntity implements Entity {
	
	private float x;
	private float y;
	private int layer;
	private float scale;
	private int frameRate;
	private EntityGraphics graphics;
	private String name;
	
	public GraphicsEntity(float x, float y, int layer, float scale, int frameRate, ArrayList<String> sprites) {
		this.name = "GraphicsEntity";
		this.x = x;
		this.y = y;
		this.layer = layer;
		this.scale = scale;
		this.frameRate = frameRate;
		this.graphics = new EntityGraphics(0);
		
		Animator animator = new Animator();
		Animation animation = new Animation(frameRate, true);
		for (String str : sprites) {
			animation.getSprites().add(SpriteLoader.getSprite(str));
		}
		animator.addAnimation(animation, "idle");
		animator.setState("idle");
		graphics.setAnimator(animator);
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + layer + "," + scale + "," + frameRate;
		for (Sprite s : graphics.getAnimator().getCurrentAnimation().getSprites()) {
			str += "," + s.getName();
		}
		return str;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.scale(scale, scale);
		graphics.render(g, x + offsetX, y + offsetY, 0, false);
		g.scale(1 / scale, 1 / scale);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getWidth() {
		return graphics.getAnimator().getCurrentSprite().getWidth();
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return graphics.getAnimator().getCurrentSprite().getHeight();
	}

	@Override
	public void setHeight(float height) {
	}

	@Override
	public void setSize(float width, float height) {
	}

	@Override
	public EntityGraphics getGraphics() {
		return graphics;
	}

	@Override
	public int getID() {
		return 26;
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public int getLayer() {
		return layer;
	}
}
