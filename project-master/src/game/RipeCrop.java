package game;

import edu.monash.fit2099.engine.Item;
/**
 * A ripened crop item which can be harvested.
 * @author jovin
 *
 */
public class RipeCrop extends Item{

	public RipeCrop() {
		super("ripeCrop",'T',false);
		super.allowableActions.add(new HarvestAction(this));
	}
	

}
