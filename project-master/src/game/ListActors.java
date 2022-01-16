package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class ListActors {
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private GameMap map;
	
	public ListActors(GameMap map) {
		this.map = map;
	}
	
	public ArrayList<Actor> getActors() {
		Location loc;
		Actor actor;
		for (int x=0; x<80; x++) {
			for (int y=0; y<25; y++) {
				loc = new Location(map, x, y);
				actor = loc.getActor();
				if (actor != null) {
					actors.add(actor);
				}
			}
		}
		return actors;
	}
}
