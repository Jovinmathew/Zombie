package game;

import edu.monash.fit2099.engine.Item;
/**
 * Food item can regenerate a human's HP.
 * @author jovin
 *
 */

public class Food extends Item {
	public Food() {
		super("Food", '+', true);
		super.allowableActions.add(new ConsumeAction(this));
	}

}
