package neon.combat;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import neon.graphics.Point;
import neon.time.TimeInfo;

public class AttackAnimation {
	
	private Rectangle hitBox;
	private int duration;
	private int attackTime = 0;
	private boolean isAttacking = false;
	private ArrayList<Point> path;
	private Point hitBoxPosition;
	
	public AttackAnimation(Rectangle hitBox, int duration) {
		this.hitBox = hitBox;
		this.duration = duration;
		path = new ArrayList<Point>();
	}
	
	public void updateAttack() {
		if (isAttacking) {
			attackTime += TimeInfo.getDelta();
			if (attackTime <= duration) {
				int pointDelta = (int) ((float) duration / (float) path.size());
				hitBoxPosition = path.get(0);
				for (int i = 1; i < path.size(); i++) {
					if (attackTime > i * pointDelta) {
						hitBoxPosition = path.get(i);
					}
				}
			} else {
				attackTime = 0;
				isAttacking = false;
				hitBoxPosition = null;
			}
		}
	}
	
	public void startAttack() {
		isAttacking = true;
		attackTime = 0;
	}
	
	public void cancelAttack() {
		isAttacking = false;
		attackTime = 0;
		hitBoxPosition = null;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public Point getHitBoxPosition() {
		return hitBoxPosition;
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}
	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public ArrayList<Point> getPath() {
		return path;
	}
	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}
}
