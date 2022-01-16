package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;

import java.util.Random;

/**
 * A Zombie.
 * 
 * added legs and arms
 * added movedLast to track if the zombie moved last turn - used for when the zombie has one leg
 * 
 * @author ram, altered by kd
 *
 */
public class Zombie extends ZombieActor {
	private int legs; 
	private int arms;
	private Random rand = new Random();
	private boolean movedLast = false;
	
	private Behaviour[] behaviours = {
			//new ZombieTalkBehaviour(),
			new PickUpBehaviour(ZombieCapability.UNDEAD),
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		legs = 2;
		arms = 2;
	}
	
	public int getLegs() {
		return legs;
	}
	
	public int getArms() {
		return arms;
	}
	/**
	 * drop arm and added a chance to drop weapon depending on numebr of remaining arms
	 * @param map
	 */
	public void dropArm(GameMap map) {
		arms = arms - 1;
		if (arms == 1) {
			if (rand.nextBoolean()) {
				Actions dropActions = new Actions();
				for (Item item : this.getInventory())
					dropActions.add(item.getDropAction());
				for (Action drop : dropActions)		
					drop.execute(this, map);
			}
		}
		else if (arms == 0) {
			Actions dropActions = new Actions();
			for (Item item : this.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(this, map);
		}
	}
	
	public void dropLeg() {
		legs = legs - 1;
	}
	

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "punches");
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * depending on number of legs, it will not call the wander and hunt behaviour
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		boolean boolchange = false;
		for (Behaviour behaviour : behaviours) {
			if (behaviour instanceof WanderBehaviour==true || behaviour instanceof HuntBehaviour==true) {
				if (!boolchange) {
					if (legs==0) {
						continue;
					}
					if (legs == 1 && movedLast) {
						movedLast = false;
						boolchange = true;
						continue;
					}
					if (legs == 1 && !movedLast) {
						movedLast = true;
						boolchange = true;
					}
					else {
						movedLast = true;
					}
				}
				else if (!movedLast) {
					continue;
				}
			}
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}
}
