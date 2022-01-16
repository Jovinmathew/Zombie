package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class Blast extends Action{
	Location blastOrigin;
	public Blast(Actor actor, String direction, GameMap map ) {
		blastOrigin = map.locationOf(actor);
	}
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String menuDescription(Actor actor) {
		return "Use Shotgun";
	}
}
