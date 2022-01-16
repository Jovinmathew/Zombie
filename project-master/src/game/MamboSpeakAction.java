package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Just a dramatic entrance for Mambo.
 * @author jovin
 *
 */

public class MamboSpeakAction extends Action{

	@Override
	public String execute(Actor actor, GameMap map) {
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return "OH NO! IT'S "+ actor;
	}

}
