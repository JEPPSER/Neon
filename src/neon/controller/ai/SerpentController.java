package neon.controller.ai;

import neon.entity.ai.enemy.Serpent;
import neon.entity.controllable.Player;
import neon.entity.projectile.Bullet;
import neon.entity.projectile.SerpentVenom;
import neon.graphics.Point;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.time.TimeInfo;

public class SerpentController implements AIController {
	
	private Serpent serpent;
	private Physics ph;
	
	private int time = 0;
	private final int COOLDOWN = 3000;
	private final int RAPID_FIRE_DELTA = 300;
	private final int RAPID_FIRE_SHOTS = 10;
	private int shotsLeft = 10;
	
	public SerpentController(Serpent serpent) {
		this.serpent = serpent;
		this.ph = serpent.getPhysics();
	}

	@Override
	public void control(Player player) {
		time += TimeInfo.getDelta();
		rapidFire(player);
	}
	
	private void rapidFire(Player player) {
		if (time >= COOLDOWN) {
			if (shotsLeft > 0 && time >= COOLDOWN + RAPID_FIRE_DELTA) {
				Point p1 = new Point(serpent.getX(), serpent.getY());
				Point p2 = new Point(player.getX(), player.getY() - 100);
				float angle = p1.angleTo(p2);
				SerpentVenom b = new SerpentVenom(p1.getX(), p1.getY(), angle, serpent.getName());
				LevelManager.addEntity(b);
				shotsLeft--;
				time = COOLDOWN;
			} else if (shotsLeft <= 0) {
				time = 0;
				shotsLeft = RAPID_FIRE_SHOTS;
			}
		}
	}

	@Override
	public void takeDamage(float damage, CollisionDirection cd) {

	}

	@Override
	public void death() {

	}

	@Override
	public boolean isInvulnerable() {
		return false;
	}

}
