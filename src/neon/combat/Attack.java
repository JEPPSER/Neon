package neon.combat;

import org.newdawn.slick.geom.Rectangle;

import neon.entity.PhysicalEntity;
import neon.graphics.Point;

public class Attack {
	
	private float damage;
	private String name;
	private AttackAnimation animation;
	
	public Attack(String name, float damage, AttackAnimation animation) {
		this.name = name;
		this.damage = damage;
		this.animation = animation;
	}
	
	public void startAttack() {
		animation.startAttack();
	}
	
	public void updateAttack() {
		if (isAttacking()) {
			animation.updateAttack();
		}
	}
	
	public void cancelAttack() {
		animation.cancelAttack();
	}
	
	public String getName() {
		return name;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public Rectangle getHitBox(PhysicalEntity entity) {
		if (animation.getHitBoxPosition() == null) {
			return null;
		}
		float x;
		float y = entity.getY() + animation.getHitBoxPosition().getY();
		if (entity.isMirrored()) {
			x = entity.getX() + entity.getWidth() - animation.getHitBoxPosition().getX() - animation.getHitBox().getWidth();
		} else {
			x = entity.getX() + animation.getHitBoxPosition().getX();
		}
		return new Rectangle(x, y, animation.getHitBox().getWidth(), animation.getHitBox().getHeight());
	}
	
	public Point getHitBoxPosition() {
		return animation.getHitBoxPosition();
	}
	
	public boolean isAttacking() {
		return animation.isAttacking();
	}
}
