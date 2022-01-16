package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Special ZombieBiteAction for zombies using the bite action
 */
public class ZombieBiteAction extends Action {
	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public ZombieBiteAction(Actor target) {
		this.target = target;
	}
	
	/**
	 * if the chance variable is met then bite a target healing for 5 health and dealing extra damage
	 * is the same as attackAction but instead has a heal and deals extra damage
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		if (rand.nextDouble()<0.4) {
			return actor + " misses bite on " + target + ".";
		}

		int damage = 15;
		String result = actor + " bites " + target + " for " + damage + " damage.";
		

		target.hurt(damage);
		actor.heal(5);
		if (!target.isConscious()) {
			Item corpse = new Corpse(target.toString());
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " bites " + target;
	}
}

