package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * action to craft weapon which is held in the allowable action for the arm and leg items
 * @author krishanu
 *
 */
public class CraftAction extends Action{
	private Item raw;
	private Item upgraded;
	
	public CraftAction(Item raw) {
		this.raw = raw;
	}
	
	/**
	 * removes the weapon form invenotry and creates the upgraded version and puts it inventory
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		actor.removeItemFromInventory(raw);
		if (raw instanceof Arm) {
			upgraded = new Club();
			actor.addItemToInventory(upgraded);
		}
		if (raw instanceof Leg) {
			upgraded = new Mace();
			actor.addItemToInventory(upgraded);
		}
		return actor + " upgraded " + raw + " to " + upgraded;
	}
	
	@Override
	public String menuDescription(Actor actor) {
//		return actor + " upgraded " + toUpgrade + " to " + upgradedTo;
		for (Item item : actor.getInventory()) {
			if (item instanceof Arm && raw instanceof Arm) 
				return "upgrade " + raw + "?";
			else if (item instanceof Leg && raw instanceof Leg)
				return "upgrade " + raw + "?";
		}
		return "error";
	}
}
