package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A zombie body part which can be used as a weapon.
 * Has a allowable action in order to call craft action.
 * @author kd
 *
 */
public class Leg extends WeaponItem {

	public Leg() {
		super("leg", '/', 7, "thwumps");
		super.allowableActions.add(new CraftAction(this));
	}

}