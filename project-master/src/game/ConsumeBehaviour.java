package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
/**
 * A class that figures out if the actor is on a food item and 
 * redirects it to ConsumeAction.
 * @author jovin
 *
 */
public class ConsumeBehaviour implements Behaviour{
	/**
	 * is the actor at max HP.a
	 */
	boolean healthcheck;
	
	/**
	 * Constructor.
	 * 
	 * @param healthcheck is the actor at max HP.
	 */
	public ConsumeBehaviour(boolean healthcheck) {
		this.healthcheck=healthcheck;
	}
	
	/**
	 * Adds the item to the inventory if the player picks it up.
	 * For NPCs, consumes the item right away if damaged.
	 */

	@Override
	public Action getAction(Actor actor, GameMap map) {
		//list of all the items at the actor's location.
		List <Item> items = map.locationOf(actor).getItems();
		
		if(!items.isEmpty()) {
			for(int i=0;i<items.size();i++) {
				Item refItem=items.get(i);
				if(refItem instanceof Food==true) {
					char actchar = actor.getDisplayChar();
					
					//puts the item into the inventory if its the player.
					if(actchar=='@') {
						refItem.getPickUpAction();
					}
					
					//consumes the item based on the max HP.
					else{
						if(healthcheck!=true) {
							return new ConsumeAction(refItem);
						}
					}
				}
			}
		}
		return null;
}
}
