package game;

import edu.monash.fit2099.engine.Item;

/**
 * A box of ammo for sniper ammo to increase sniper ammo
 * 
 * @author kd
 *
 */
public class SniperAmmo extends Item {

	public SniperAmmo() {
		super("Sniper Rounds", ';', false);
		this.allowableActions.add(new PickUpAmmoAction(this, "sniper"));
	}
	
}