package game;

import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * 
 * A class for the farmer specifically. 
 * Lets the farmer sow, fertilize and harvest crops. 
 * 
 * @author jovin
 *
 */
public class FarmBehaviour implements Behaviour{
	
	
	private Random random = new Random();
	/**
	 * The actor checks if it is standing on a (ripe/unripe) crop. 
	 * Starts fertilizing/harvesting a crop if there is one. 
	 * If not, takes a random chance to sow a crop.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		List <Item> items = map.locationOf(actor).getItems();
		
		if(!items.isEmpty()) {
			for(int i=0;i<items.size();i++) {
				Item refItem=items.get(i);
				if(refItem instanceof Crop==true) {
					return new FertilizeAction(refItem);
				}
				else if(refItem instanceof RipeCrop==true){
					return new HarvestAction(refItem);
				}
			}
		}
		else {
			if(random.nextDouble()<0.33) {
				return new SowAction();
			}						
		}
		return null;
	}
}