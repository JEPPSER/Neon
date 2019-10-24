package neon.entity.area;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import neon.physics.Physics;

public class TextTrigger extends Trigger {
	
	private String text = "";
	private boolean showText = false;
	private float textX;
	private float textY;
	private TrueTypeFont font;
	
	public TextTrigger() {
		this.name = "TextTrigger";
		this.physics = new Physics(0f, 0f);
		font = new TrueTypeFont(new Font("Helvetica", Font.BOLD, 50), true);
	}
	
	@Override
	public void unTriggered() {
		showText = false;
	}

	@Override
	public void triggered() {
		showText = true;
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
		this.text = text;
		this.textX = textX;
		this.textY = textY;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		//graphics.render(g, x + offsetX, y + offsetY, 0, false);
		if (showText) {
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString(text, textX, textY);
		}
	}
}
