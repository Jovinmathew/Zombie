package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
/**
 * 
 * Special Action for consuming food and regain hp for Humans.
 * @author jovin
 *
 */


public class ConsumeAction extends Action{
	
	/**
	 * The item to be used.
	 */
	protected Item item;
	
	/**
	 * Constructor.
	 * 
	 * @param item consumable food item.
	 */
	public ConsumeAction(Item item) {
		this.item = item;
	}

	/**
	 * Heals the actor for 5 hp and removes the item from the game map.
	 * 
	 * @param actor the actor who consumed the food item
	 * @param map the map that actor is currently on
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if(actor.getDisplayChar()=='@') {
			actor.heal(5);
			actor.removeItemFromInventory(item);
		}
		else {
			actor.heal(5);
			map.locationOf(actor).removeItem(item);
		}
		return actor+" consumed food item and regained 5 hp";
	}
	
	
	@Override
	public String menuDescription(Actor actor) {
		return actor+ " consume food";
	}

}
