package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.GameMap;

/**
 * A vehicle to travel.
 * @author jovin
 *
 */
public class Vehicle extends Item {
	
	/**
	 * Constructor
	 * 
	 * @param map stores the destination.
	 */
	public Vehicle(GameMap map) {
		super("vehicle",'O',false);
		super.allowableActions.add(new TravelAction(map));
	}

}
