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
 * returns a random saying form the sayings list
 * @author krishanu
 *
 */
public class ZombieTalkAction extends Action{
	private String[] sayings = {"argh", "brains", "blah", "cliche zombie saying"};
	private Random rand = new Random();
	private String phrase;
	
	public ZombieTalkAction() {}
	
	/**
	 * choses a saying randomly and returns it
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		int sayIndex = rand.nextInt(4);
		phrase = sayings[sayIndex];
		return actor + " says: " + phrase;
	}
	@Override
	public String menuDescription(Actor actor) {
		return actor + " says: " + phrase;
	}
}

