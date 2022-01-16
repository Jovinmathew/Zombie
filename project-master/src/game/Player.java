package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.Weapon;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private int turnsAimed = 0;
	private Actor aimingAt = null;
	
	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}
	/**
	 * Spawns the Mambo Marie.
	 * MamboMarie has a 5% chance to spawn and always spawns on the bottom left corner of the map.
	 * @param map
	 */
	public void MamboMarieSpawn(GameMap map) {
		
			Random rand = new Random();
			if(rand.nextInt(100)<5) {
				
				if(map.at(0, 24).canActorEnter(new MamboMarie("Mambo Marie"))) {
				map.at(0, 24).addActor(new MamboMarie("Mambo Marie"));
				marieCheck=true;
				}
				
				}
		}
	
	public Action LossCheck(GameMap map) {
		ListActors listactors =  new ListActors(map);
		ArrayList<Actor> actors = listactors.getActors();
		boolean flag = false;
		for(Actor checkactor: actors) {
			if(checkactor.getDisplayChar()=='H'||checkactor.getDisplayChar()=='F')
				flag=true;
		}
		
		if(!flag) {
			return new QuitGameAction();
		}
		return null;
	}
	
	public Action WinCheck(GameMap map) {
		ListActors listactors =  new ListActors(map);
		ListItems listitems = new ListItems(map);
		
		ArrayList<Actor> actors = listactors.getActors();
		
		boolean flag = false;
		for(Actor checkactor: actors) {
			if(checkactor.getDisplayChar()=='Z')
				flag=true;
		}
		
		ArrayList<Item> items = listitems.getItems();
		
		if(!flag) {
			flag=true;
			for(Item checkitem: items) {
				if(checkitem.getDisplayChar()=='$') {
					flag=false;
					break;
					}
			}
		}
		
		if(!flag) {
			return new QuitGameAction();
		}
		return null;
		}
	
	
	
	public void setAimingAt(Actor target) {
		aimingAt = target;
	}
	
	public void aim() {
		turnsAimed++;
	}
	
	public void breakAim() {
		turnsAimed = 0;
		setAimingAt(null);
	}
	
	public int getTurnsAimed() {
		return turnsAimed;
	}
	
	public Actor getTarget() {
		return aimingAt;
	}
	
	@Override
	public void hurt(int points) {
		hitPoints -= points;
		breakAim();
	}
	
	@Override
	public Weapon getWeapon() {
		for (Item item : inventory) {
			if (item.asWeapon() != null && !(item instanceof Shotgun) && !(item instanceof Sniper))
				return item.asWeapon();
		}
		return getIntrinsicWeapon();
	}
	

	/**
	 * altered to remove the allowable actions form standing on an arm/leg (for upgrade) or food
	 * If the item is in the inventory then it is allowed to stay in the actions
	 */
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		//Spawning MamboMarie
		
		if(new MapChecker().isCompound(map)) {
			if(!marieCheck)
				MamboMarieSpawn(map);
			Action quitGame= LossCheck(map);
			if(quitGame!=null)
			{
				display.println("All the humans are dead. You failed to save the compound.\nYOU LOSE");
				return quitGame;
			}
			quitGame= WinCheck(map);
			if(quitGame!=null) {
				display.println("Thanks to you the town is safe. The zombie epidemic is no more.\nYOU WIN");
				return quitGame;
			}
		}
		
		
		
		
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		boolean craftable = false;
		boolean consumable= false;
		boolean shotgunLoad = false;
		boolean sniperLoad = false;
		Action theCAction = null;
		Action theFAction = null;
		Action theGunAction = null;
		String mode = null;
		for (Action action:actions) {
			if (action instanceof CraftAction) {
				theCAction = action;
				for (Item item:this.getInventory()) {
					if (item instanceof Arm || item instanceof Leg)
						craftable = true;
				}
			}
			if(action instanceof ConsumeAction) {
				theFAction = action;
				for (Item item:this.getInventory()) {
					if(item instanceof Food)
						consumable=true;
				}
			}
			if(action instanceof PickUpAmmoAction) {
				mode = ((PickUpAmmoAction) action).getMode();
				theGunAction = action;
				if (mode=="shotgun") {
					for (Item item:this.getInventory()) {
						if(item instanceof Shotgun)
							shotgunLoad=true;
					}
				}
				else if (mode=="sniper") {
					for (Item item:this.getInventory()) {
						if(item instanceof Sniper)
							sniperLoad=true;
					}
				}
			}

			
		}
		if(!craftable) 
			actions.remove(theCAction);

		if(!consumable)
			actions.remove(theFAction);
		
		if(!shotgunLoad && !sniperLoad)
			actions.remove(theGunAction);
		
		actions.add(new QuitGameAction());
		
		for (Item item:this.getInventory()) {
			if (item instanceof Shotgun)
				if (((Shotgun)item).enoughAmmo())
					actions.add(new UseGunAction("shotgun", display, lastAction));
			if (item instanceof Sniper)
				if (((Sniper)item).enoughAmmo())
					actions.add(new UseGunAction("sniper", display, lastAction));
		}
		
		
		
		return menu.showMenu(this, actions, display);
	}
}
