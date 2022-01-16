package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class PickUpAmmoAction extends Action{
	private String mode;
	private Item ammoBox;
	
	public PickUpAmmoAction(Item item, String mode) {
		this.ammoBox = item;
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	@Override
	public String execute(Actor actor, GameMap map) {
		for(Item item : actor.getInventory()) {
			if (item instanceof Shotgun && mode=="shotgun") {
				((Shotgun) item).loadAmmo(2);
				map.locationOf(actor).removeItem(ammoBox);
				return "Picked up Shotgun Rounds";
			}
			if (item instanceof Sniper && mode=="sniper") {
				((Sniper) item).loadAmmo(2);
				map.locationOf(actor).removeItem(ammoBox);
				return "Picked up Sniper Rounds";
			}
		}
		return "error in PickUpAmmoAction.execute";
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Pick Up " + mode + " ammo";
	}
	
}
