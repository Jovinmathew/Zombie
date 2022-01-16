package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * Action for marie to spawn more zombies on the map.
 * @author jovin
 *
 */
public class ZombieSpawnAction extends Action{
	
	protected Random rand = new Random();
	
	/**
	 * a loop which runs and spawns zombies at random location.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		for(int i=0;i<5;i++) {
			int x=rand.nextInt(79);
			int y=rand.nextInt(24);
			String zombiename = "mambojambo "+x;
			if(map.at(x, y).canActorEnter(actor))
				map.at(x, y).addActor(new Zombie(zombiename));
			else
				i--;
		}
		return menuDescription(actor);
	}
	
	@Override
	public String menuDescription(Actor actor) {
		return actor+" used her spell and here comes a horde of zombies.";
	}

}
