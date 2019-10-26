package neon.entity.ai;

public abstract class Enemy extends AIEntity {

	protected float health;
	protected float maxHealth;
	protected float damage;
	
	public abstract void takeDamage(float damage);
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
}
