package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * A special action class for farmer, giving him the ability to sow a crop around him.
 * @author jovin
 *
 */
public class SowAction extends Action{
	
	
	
	private boolean checkDuplicates(List<Item> items) {
		if(!items.isEmpty()) {
			for(int i=0;i<items.size();i++) {
				Object refItem = items.get(i);
				if(refItem instanceof Crop==true) {
					return true;
				}
			}
		}
			
		return false;
		
	}
	
	/**
	 * The actor checks if there are empty locations around it and sows a crop at the first patch of dirt it finds.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
            	if(checkDuplicates(destination.getItems())==false) {
            	destination.addItem(new Crop());
            	break;
            	}
            }
		}
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " has sown a crop.";
	}

}
