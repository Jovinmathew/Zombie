package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
/**
 * Class representing Mambo Marie
 * @author jovin
 *
 */
public class MamboMarie extends ZombieActor{
	
	/**
	 * records the number of turns passed since Mambo Marie spawned.
	 */
	private int turnCount;
	
	/**
	 * Constructor 
	 * @param name name of the actor.
	 */
	public MamboMarie(String name) {
		super(name, 'M', 50, ZombieCapability.UNDEAD);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The default action is to wander. 
	 * If Mambo has just spawned, MamboSpeakAction() is called.
	 * Every 10 turns the ZombieSpawnAction() is called to spawn more zombies.
	 * On the 30th turn, Mambo is removed from the map using MamboVanishAction().
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		Action action = new WanderBehaviour().getAction(this, map);
		
		if(turnCount%10==0&&turnCount!=0) {
			action = new ZombieSpawnAction();
		}
		else if(turnCount==0) {
			action = new MamboSpeakAction();
		}
		else if(turnCount==30) {
			
			marieCheck=false;
			action = new MamboVanishAction();
			
		}
		turnCount++;
		if (action != null)
			return action;
			
		
			
		return new DoNothingAction();
	}
	

}
