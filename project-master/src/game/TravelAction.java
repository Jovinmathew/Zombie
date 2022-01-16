package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * Moves the player from one map to another.
 * @author jovin
 *
 */
public class TravelAction extends Action{
	/**
	 * the map to be travelled to
	 */
	protected GameMap travelledMap;
	/**
	 * Constructor 
	 * 
	 * @param map the map that the player is going to travel to.
	 */
	public TravelAction(GameMap map) {
		travelledMap=map;
	}
	
	/**
	 * Moves the player to the other map.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
			
			map.moveActor(actor, travelledMap.at(79,23));
			return "VROOOOMMMMMMMM......";
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Do you wish to travel to another map.";
	}
	
	

}
