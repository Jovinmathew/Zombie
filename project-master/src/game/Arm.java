package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A zombie body part which can be used as a weapon.
 * Has a allowable action in order to call craft action.
 * @author kd
 *
 */
public class Arm extends WeaponItem {

	public Arm() {
		super("arm", '-', 6, "thwacks");
		super.allowableActions.add(new CraftAction(this));
	}

}