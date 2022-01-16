package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class Snipe extends Action{
	public Sniper theSniper;
	public Actor actor;
	public Snipe(Sniper theSniper, Actor actor) {
		this.theSniper = theSniper;
		this.actor = actor;
	}
	@Override
	public String execute(Actor actor, GameMap map) {
		return null;
	}
	@Override
	public String menuDescription(Actor actor) {
		return "Use Sniper";
	}
}
