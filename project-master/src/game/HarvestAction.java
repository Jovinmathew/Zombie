package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
/**
 * Special action for player and farmer to be able to harvest the crop and turn into food.
 * @author jovin
 *
 */
public class HarvestAction extends Action{
	/**
	 * item that actor is standing on.
	 */
	protected Item item;
	
	/**
	 * Constructor 
	 * 
	 * @param item ripened crop that actor is standing on.
	 */
	public HarvestAction(Item item) {
		this.item = item;
	}
	
	/**
	 * If the actor harvests a crop, the food item goes to his inventory.
	 * If a farmer harvests a crop, the food item is dropped at the farmer's location.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if(actor.getDisplayChar()!='@') {
			map.locationOf(actor).removeItem(item);
			map.locationOf(actor).addItem(new Food());
		}
		else {
			map.locationOf(actor).removeItem(item);
			actor.addItemToInventory(new Food());
		}
		return actor + " harvested a ripened crop.";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor+" harvest a crop.";
	}

}
