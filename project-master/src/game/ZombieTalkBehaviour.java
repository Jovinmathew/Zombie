package game;

import java.util.Random;
/**
 * 
 * 
 */

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * is a behaviour that calls the zombieTalkAction if the 10% chance is met, otherwise return null
 * @author krishanu
 *
 */
public class ZombieTalkBehaviour implements Behaviour {
	private Random rand = new Random();
	public ZombieTalkBehaviour() {}
	
	public Action getAction(Actor actor, GameMap map) {
		if (rand.nextDouble()>0.1){
			return null;
		}
		return new ZombieTalkAction();
	}
}
