package neon.combat;

import java.util.HashMap;

public class Combat {
	
	private HashMap<String, Attack> attacks;
	private String currentAttack = "";
	
	public Combat() {
		attacks = new HashMap<String, Attack>();
	}
	
	public void startAttack(String attack) {
		if (!currentAttack.equals("")) {
			attacks.get(currentAttack).cancelAttack();
		}
		
		attacks.get(attack).startAttack();
		currentAttack = attack;
	}
	
	public void addAttack(Attack attack) {
		attacks.put(attack.getName(), attack);
	}
	
	public Attack getCurrentAttack() {
		return attacks.get(currentAttack);
	}
	
	public void updateAttacks() {
		if (!currentAttack.equals("")) {
			if (attacks.get(currentAttack).isAttacking()) {
				attacks.get(currentAttack).updateAttack();
			} else {
				currentAttack = "";
			}
		}
	}
	
	public boolean isAttacking() {
		if (currentAttack.equals("") || !attacks.get(currentAttack).isAttacking()) {
			return false;
		}
		return true;
	}
}
