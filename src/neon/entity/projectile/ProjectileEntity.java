package neon.entity.projectile;

import neon.entity.PhysicalEntity;
import neon.entity.ai.enemy.Enemy;
import neon.entity.controllable.Player;
import neon.entity.terrain.TerrainEntity;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;

public abstract class ProjectileEntity extends PhysicalEntity {

	protected float damage = 1;
	protected float angle = 0;
	protected float speed = 1;
	protected float weight = 0;
	protected String sourceEntity;

	@Override
	public void handleCollision(PhysicalEntity other) {
		if (collidingEntity == other && collisionDirection != CollisionDirection.NONE
				&& !other.getName().equals(sourceEntity)) {
			if (other instanceof Player) {
				((Player) other).takeDamage(damage);
			} else if (other instanceof Enemy) {
				((Enemy) other).takeDamage(damage, collisionDirection);
			}
			if (other instanceof Player || other instanceof Enemy || other instanceof TerrainEntity) {
				LevelManager.removeEntity(this);
			}
		}
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
