package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

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
	public AttackAction(Actor target) {
		this.target = target;
	}
	
	/**
	 * 
	 * @param target - the target to be 
	 * @param map - the map passed on from the execute action
	 * @return the string to be added on the the end of execute method string in order to say which limb has been dropped.
	 */
	private String dropLimb(Actor target, GameMap map) {
		boolean toDropArm; // where true = arm is to be dropped; false = leg is to be dropped
		int legs = ((Zombie) target).getLegs();
		int arms = ((Zombie) target).getArms();
		if (arms > 0 && legs > 0) {
			if (rand.nextBoolean())
				toDropArm = false;
			else
				toDropArm = true;
		}
		else if (arms==0) {
			toDropArm = false;
		}
		else if (legs==0) {
			toDropArm = true;
		}
		else
			return "";
		if (toDropArm == true) {
			((Zombie) target).dropArm(map);
			
			map.locationOf(target).addItem(new Arm());
			return " " + target + " has dropped an arm";
		}
		else if (toDropArm == false) {
			((Zombie) target).dropLeg();
			map.locationOf(target).addItem(new Leg());
			return " " + target + " has dropped a leg";
		}
		return "something's wrong in dropping limbs... I can feel it"; // for debugging
	}
	
	/**
	 * execute method altered
	 * If a zombie is attacked there is 25% chance of dropping a limb by calling the dropLimb method
	 * if a zombie is killed they drop a portableItem object which represents a normal cropse
	 * If a human is killed they drop a Corpse object 
	 *  
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		
		//DROPPING LIMBS
		//if target is a zombie then see if dropping a limb is possible
		if (target.hasCapability(ZombieCapability.UNDEAD)) {
			// if positive than remove a limb
			if (rand.nextDouble()<0.25) {
				result += dropLimb(target, map);
			}
		}

		target.hurt(damage);
		if (!target.isConscious()) {
			if (target instanceof Zombie) {
				Item corpse = new PortableItem("dead " + target, '%');
				map.locationOf(target).addItem(corpse);
			}
			else if(target instanceof MamboMarie) {
				Item corpse = new PortableItem("dead " + target, '$');
				map.locationOf(target).addItem(corpse);
			}
			else if (target instanceof Human){
				Item corpse = new Corpse(target.toString());
				map.locationOf(target).addItem(corpse);
			}
			
			
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
		return actor + " attacks " + target;
	}
}
