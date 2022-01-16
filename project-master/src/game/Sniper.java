package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A Sniper weapon
 * Has a allowable action in order to call Snipe to shoot the weapon.
 * @author kd
 *
 */
public class Sniper extends WeaponItem {
	private int ammo = 3;
	
	public Sniper() {
		super("Sniper", '|', 20, "cracks");
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	public void useAmmo() {
		ammo--;
	}
	
	public boolean enoughAmmo() {
		if (ammo > 0) {
			return true;
		}
		else
			return false;
	}
	
	public void loadAmmo(int newAmmo) {
		ammo+=newAmmo;
	}

}