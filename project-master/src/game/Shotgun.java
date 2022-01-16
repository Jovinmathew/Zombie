package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A Shotgun weapon
 * Has a allowable action in order to call Blast to shoot the weapon.
 * @author kd
 *
 */
public class Shotgun extends WeaponItem {
	public int ammo = 3;
	
	public Shotgun() {
		super("Shotgun", 'S', 20, "Skudoosh");
	}
	
	public void useAmmo() {
		ammo--;
	}
	
	public boolean enoughAmmo() {
		if (ammo>0)
			return true;
		else
			return false;
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	public void loadAmmo(int newAmmo) {
		ammo+=newAmmo;
	}

}