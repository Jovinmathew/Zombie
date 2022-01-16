package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * Action for marie to vanish from the map.
 * @author jovin
 *
 */
public class MamboVanishAction extends Action{
	/**
	 * Removes marie from the map.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		map.removeActor(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return "*POOF* "+actor+" has vanished.";
	}

}
