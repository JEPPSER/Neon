package neon.entity.ai;

import neon.time.TimeInfo;

public abstract class Enemy extends AIEntity {

	protected float health;
	protected float maxHealth;
	protected float damage;
	protected float despawn = 1000;
	protected float despawnTimer = 0;
	protected boolean isDead = false;
	
	public abstract void takeDamage(float damage);
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void death() {
		if (graphics.getAnimator().hasState("death")) {
			graphics.getAnimator().setState("death");
		}
		this.ai.death();
		this.collision.getHitbox().setBounds(0, 0, 0, 0);
		this.collision.setMovable(false);
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public boolean canDespawn() {
		despawnTimer += TimeInfo.getDelta();
		if (despawnTimer > despawn) {
			return true;
		}
		return false;
	}
}
