package neon.graphics.animation;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import neon.time.TimeInfo;

public class TextAnimation {
	
	private float x;
	private float y;
	private TrueTypeFont font;
	private AnimationType type;
	
	private boolean showText = false;
	private int textTimer = 0;
	
	public void start() {
		if (type == AnimationType.DROP) {
			startDrop();
		}
	}
	
	public void stop() {
		if (type == AnimationType.DROP) {
			stopDrop();
		}
	}
	
	private void startDrop() {
		showText = true;
		textTimer += TimeInfo.getDelta();
		if (textTimer > y) {
			textTimer = (int) y;
		}
	}
	
	private void stopDrop() {
		textTimer -= TimeInfo.getDelta();
		if (textTimer < 0) {
			textTimer = 0;
			showText = false;
		}
	}
	
	private void renderDrop(Graphics g, String text) {
		g.setFont(this.font);
		if (showText) {
			g.drawString(text, x, textTimer);
		}
	}
	
	public void render(Graphics g, String text, float offsetX, float offsetY) {
		if (type == AnimationType.DROP) {
			renderDrop(g, text);
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public TrueTypeFont getFont() {
		return font;
	}

	public void setFont(TrueTypeFont font) {
		this.font = font;
	}

	public AnimationType getType() {
		return type;
	}

	public void setType(AnimationType type) {
		this.type = type;
	}
}