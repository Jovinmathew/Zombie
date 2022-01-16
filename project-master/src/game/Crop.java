package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * An unripe crop.
 * 
 * @author jovin
 *
 */
public class Crop extends Item{
	/**
	 * number of turns
	 */
	public int age = 0;

	public Crop() {
		super("unripeCrop",'t',false);
	}
	
	/**
	 * Primarily called by FertilizeAction, decreases crop's time to ripen. 
	 */
	
	public void fertilizecall() {
		age+=10;
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		
		if (age >= 20) {
			location.removeItem(this);
			location.addItem(new RipeCrop());
	}
}
}
