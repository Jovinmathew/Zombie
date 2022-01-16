package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * Adds the option for the player to quit the game.
 * @author jovin
 *
 */
public class QuitGameAction extends Action{
	
	/**
	 * Removes the player from the map.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		map.removeActor(actor);
		return " ";
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return "Do you wish to quit the game?";
	}

}
