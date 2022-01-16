package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
/**
 * Special action for farmers to be able fertilize crops. 
 * @author jovin
 *
 */
public class FertilizeAction extends Action{
	/**
	 * item that actor is standing on.
	 */
	protected Item item;
	
	/**
	 * Constructor 
	 * 
	 * @param item crop item that the actor is standing on.
	 */
	public FertilizeAction(Item item) {
		
		this.item = item;
	}
	
	/**
	 * On execution, this methods calls the fertilizecall for this crop 
	 * and decreases its time to ripen.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		((Crop) item).fertilizecall();
		return menuDescription(actor);
		
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor+ " fertilized a crop.";
	}

}
