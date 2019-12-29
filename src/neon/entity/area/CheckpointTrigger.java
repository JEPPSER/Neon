package neon.entity.area;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import neon.graphics.animation.AnimationType;
import neon.graphics.animation.TextAnimation;
import neon.level.Checkpoint;
import neon.level.LevelManager;

public class CheckpointTrigger extends Trigger {
	
	private TrueTypeFont font;
	private TextAnimation anim;
	private boolean isTriggered = false;
	private Checkpoint checkpoint;
	
	public CheckpointTrigger() {
		this.name = "CheckpointTrigger";
		font = new TrueTypeFont(new Font("Helvetica", Font.BOLD, 30), true);
		text = "Checkpoint has been reached.";
	}

	@Override
	public int getID() {
		return 5;
	}

	@Override
	public void triggered() {
		anim.setX((float) Display.getWidth() / scale / 2f - (float) this.font.getWidth(text) / 2f);
		anim.show();
		if (!isTriggered) {
			isTriggered = true;
			if (checkpoint == null || checkpoint != LevelManager.getCheckpoint()) {
				checkpoint = new Checkpoint(x, y);
				LevelManager.setCheckpoint(checkpoint);
			}
		}
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
		anim = new TextAnimation();
		anim.setFont(font);
		anim.setType(AnimationType.FADE);
		anim.setY(100);
		anim.setX((float) Display.getWidth() / scale / 2f - (float) this.font.getWidth(text) / 2f);
	}

	@Override
	public void unTriggered() {
		anim.stop();
		isTriggered = false;
	}
	
	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + getWidth() + "," + getHeight();
		return str;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
//		g.setColor(Color.red);
//		g.drawRect(x + offsetX, y + offsetY, width, height);
		anim.render(g, text, scale);
	}
}
