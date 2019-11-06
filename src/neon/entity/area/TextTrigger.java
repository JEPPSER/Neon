package neon.entity.area;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import neon.graphics.animation.AnimationType;
import neon.graphics.animation.TextAnimation;
import neon.physics.Physics;

public class TextTrigger extends Trigger {
	
	private String text = "";
	private TrueTypeFont font;
	private TextAnimation anim;
	
	public TextTrigger() {
		this.name = "TextTrigger";
		this.physics = new Physics(0f, 0f);
		font = new TrueTypeFont(new Font("Helvetica", Font.BOLD, 50), true);
	}
	
	@Override
	public void unTriggered() {
		anim.stop();
	}

	@Override
	public void triggered() {
		anim.show();
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
		this.text = text;
		anim = new TextAnimation();
		anim.setFont(font);
		anim.setType(AnimationType.FADE);
		anim.setY(textY);
		anim.setX((float) Display.getWidth() / scale / 2f - (float) this.font.getWidth(text) / 2f);
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		anim.render(g, text, scale);
		//graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}

	@Override
	public int getID() {
		return 1;
	}
}
