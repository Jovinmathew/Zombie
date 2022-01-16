package game;

import edu.monash.fit2099.engine.Item;

/**
 * A box of ammo for shotgun ammo to increase shotgun ammo
 * 
 * @author kd
 *
 */
public class ShotgunAmmo extends Item {

	public ShotgunAmmo() {
		super("Shotgun Shells", ':', false);
		this.allowableActions.add(new PickUpAmmoAction(this, "shotgun"));
	}
	
}
