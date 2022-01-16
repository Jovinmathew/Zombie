package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

import java.util.List;

/**
 * allows zombies to pick up weapons if they 
 * @author krishanu
 *
 */
public class PickUpBehaviour implements Behaviour{
	private ZombieCapability actorTeam;
	
	public PickUpBehaviour(ZombieCapability actorTeam) {
		this.actorTeam = actorTeam;
	}
	
	/**
	 * add the weapon to the inventory if it meets the requirements
	 */
	public Action getAction(Actor actor, GameMap map) {
		List<Item> inventory = actor.getInventory();
		if (inventory.size() != 0 || ((Zombie) actor).getArms()==0)// if no arms or invernotry already has wepaon don't pick up 
			return null;
		List<Item> items = map.locationOf(actor).getItems();
		if (items.size() == 0)
			return null;
		else if (actorTeam == ZombieCapability.UNDEAD) {
			for (Item item : items) {
				if (item.asWeapon() != null) {
					Action pickup = new PickUpItemAction(items.get(0));
					return pickup;
				}
			}
		}
		return null;
	}
}
