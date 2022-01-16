package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class representing a farmer.
 * @author jovin
 *
 */
public class Farmer extends Human {
	private Behaviour[] behaviours = {
			new FarmBehaviour(),
			new ConsumeBehaviour(ifMaxHealth()),
			new WanderBehaviour()
			
	};
	 
	/**
	 * Constructor.
	 *
	 * @param name the Farmer's display name
}
	*/
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	/**
	 * If the farmer finds a crop, it will start working on it.
	 * If the farmer has a food item in his current location, it will try consuming it.
	 * If not, it will wander randomly.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}
		
	}