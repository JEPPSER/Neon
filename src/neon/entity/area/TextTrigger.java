package neon.entity.area;

import java.awt.Font;

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
		anim.start();
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
		this.text = text;
		anim = new TextAnimation();
		anim.setFont(font);
		anim.setType(AnimationType.FADE);
		anim.setX(textX);
		anim.setY(textY);
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		anim.render(g, text, offsetX, offsetY);
		//graphics.render(g, x + offsetX, y + offsetY, 0, false);
	}
}
