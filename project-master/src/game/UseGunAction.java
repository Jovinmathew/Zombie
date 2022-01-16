package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Class used for when a gun (sniper/shotgun) is used
 * It works by creating a submenu for which target/direction you want to shoot.
 * Then calls blast for shotgun or snipe for sniper to carry out the actual attack.
 * @author krishanu
 *
 */
public class UseGunAction extends Action{
	private String mode;
	private Display display;
	private Action lastAction;
	private Random rand = new Random();
	private int shotgunDamage = 40;
	private int sniperDamage = 40;
	private int ammoLeft;
	private ArrayList<Actor> targets = new ArrayList<Actor>();
	private ArrayList<Character> options = new ArrayList<Character>();
	private Actor target;
	
	public UseGunAction(String mode, Display display, Action lastAction) {
		this.mode = mode;
		this.display = display;
		this.lastAction = lastAction;
	}
	
	/**
	 * Creates a submenu for the sniper/shotgun in order to display the options of which target/direction to chose
	 * @param actor the player
	 * @param map the map
	 * @return char key. this is the option chosen by the submenu - used by blast/snipe
	 */
	private char showSubMenu(Actor actor, GameMap map) {
		int x;
		int y;
		if (mode == "shotgun") {
			Location origin = map.locationOf(actor); 
			x = origin.x();
			y = origin.y()-1;
			//YOU CAN'T SHOOT INTO A BOUNDARY - so if that direction isn't a boundary, give it as an option
			if (y>=0) { //check boundary
				options.add('1');
				display.println("1: Fire North");
			}
			x = origin.x()+1;
			y = origin.y()-1;
			if (x<=79 && y>=0) {
				options.add('2');
				display.println("2: Fire North-East");
			}
			x = origin.x()+1;
			y = origin.y();
			if (x<=79) {
				options.add('3');
				display.println("3: Fire East");
			}
			x = origin.x()+1;
			y = origin.y()+1;
			if (x<=79 && y<=24) {
				options.add('4');
				display.println("4: Fire South-East");
			}
			x = origin.x();
			y = origin.y()+1;
			if (y<=24) {
				options.add('5');
				display.println("5: Fire South");
			}
			x = origin.x()-1;
			y = origin.y()+1;
			if (x>=0 && y<=24) {
				options.add('6');
				display.println("6: Fire South-West");
			}
			x = origin.x()-1;
			y = origin.y();
			if (x>=0) {
				options.add('7');
				display.println("7: Fire West");
				
			}
			x = origin.x()-1;
			y = origin.y()-1;
			if (x>=0 && y>=0) {
				options.add('8');
				display.println("8: Fire North-West");
			}
			
			// Loop till valid input is found
			char key = 0;
			do {
				key = display.readChar();
			} while (!options.contains(key));
			
			return key;
		}
		else if (mode == "sniper") {
			ListActors listActorsObj = new ListActors(map);
			ArrayList<Actor> actors = listActorsObj.getActors(); // get list of all actors
			//Loop to find actors that are zombies
			for (Actor potTarget : actors) { // go through potential targets
				if (potTarget instanceof Zombie || potTarget instanceof MamboMarie) {
					targets.add(potTarget);
				}
			}
			if(targets.isEmpty()) {
				return 'N'; //no available targets
			}
			
			//If the already aimed at someone show them
			Actor currTarget = ((Player)actor).getTarget();
			if (currTarget == null)
				display.println("Choose Target");
			else
				display.println("Choose Target. Currently aimed at " + currTarget + " for " + ((Player)actor).getTurnsAimed() + " turns");
			
			//display the options
			char option = 'a';
			for (Actor potTarget : targets) {
				options.add(option);
				display.println(option + ": " + potTarget.toString());
				if(option=='z') // can't have more than 26 options
					break;
				option++;
			}
			
			// Loop till valid input is found
			char key = '0';
			do {
				try {
					key = display.readChar();
				}
				catch(Exception e){
					continue;
				}
			} while (!options.contains(key));
			
			target = targets.get(options.indexOf(key));
			
			return key;
		}
		
		
		return 'E'; // meaning error
	}
	
	/**
	 * kills a target by removing them and leaving a body on the map
	 * Is only called if the target is unconscious after taking a hit
	 * @param map - map
	 * @return a string saying that their dead
	 */
	public String kill(GameMap map) {
		if (target instanceof Zombie) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
		}
		else if(target instanceof MamboMarie) {
			Item corpse = new PortableItem("dead " + target, '$');
			map.locationOf(target).addItem(corpse);
		}
		else if (target instanceof Human){
			Item corpse = new Corpse(target.toString());
			map.locationOf(target).addItem(corpse);
		}
		
		Actions dropActions = new Actions();
		for (Item item : target.getInventory())
			dropActions.add(item.getDropAction());
		for (Action drop : dropActions)		
			drop.execute(target, map);
		map.removeActor(target);	
		
		return " dead";
	}
	
	/**
	 * Gets the key and gives a submenu with options of shoot or aim. Then carries out the respective action
	 * @param key - the option key for the target
	 * @param actor - the player
	 * @param map - map
	 * @return string saying what was done - either miss or hit for x damage etc.
	 */
	private String snipe(int key, Actor actor, GameMap map) {
		if (key=='N') {
			return "No available targets";
		}
		//Find out how much ammo left
		for(Item item :actor.getInventory()) {
			if (item instanceof Sniper) {
				int ammoLeft = ((Sniper) item).getAmmo();
			}
		}
		//if target is different to currently aimed target... break concentration
		if (((Player)actor).getTarget()!=target) {
			((Player)actor).breakAim();
		}
		
		//Display options depending on how many times already aimed
		display.println("Aim or Fire?");
		options.clear();
		if(((Player)actor).getTurnsAimed()<=1) {
			display.println("1: Aim");
			display.println("2: Fire");
			options.add('1');
			options.add('2');
		}
		else { // once you've aimed for 3 you can only fire
			display.println("2: Fire");
			options.add('2');
		}
		

		// Loop till valid input is found
		char key2 = '1';
		do {
			try {
				key2 = display.readChar();
			}
			catch(Exception e){
				continue;
			}
		} while (!options.contains(key2));
		if (key2 == '1') { // aiming
			((Player)actor).setAimingAt(target);
			((Player)actor).aim();
			return "Sniper aimed at " + target.toString() + ". Turns aimed: " + ((Player)actor).getTurnsAimed();
			
		}
		else if (key2 == '2') { // firing
			for(Item item :actor.getInventory()) {
				if (item instanceof Sniper) {
					((Sniper) item).useAmmo();
					ammoLeft = ((Sniper) item).getAmmo();
				}
			}
			
			int turnsAimed = ((Player)actor).getTurnsAimed();
			((Player)actor).breakAim(); //reset aim
			((Player)actor).setAimingAt(null); //remove target
			
			if (turnsAimed==0) {
				if (rand.nextDouble()<0.75) {
					target.hurt(sniperDamage);
					if (target.isConscious()) 
						return "Sniped " + target.toString() + " for " + sniperDamage + ". Ammo left: " + ammoLeft;
					else {
						return "Sniped " + target.toString() + kill(map) + ". Ammo left: " + ammoLeft;
					}
				}
				else {
					return "Sniper missed";
				}
			}
			else if (turnsAimed==1) {
				if (rand.nextDouble()<0.90) {
					int damage = sniperDamage*2;
					target.hurt(damage);
					if (target.isConscious())
						return "Sniped " + target.toString() + " for " + damage + ". Ammo left: " + ammoLeft;
					else {
						return "Sniped " + target.toString() + kill(map) + ". Ammo left: " + ammoLeft;
					}
				}
				else {
					return "Sniper missed";
				}
			}
			else if (turnsAimed>1) {
				target.hurt(200);
				return "Sniped " + target.toString() + kill(map) + ". Ammo left: " + ammoLeft;
			}
			
			
			
		}
		return "error in UseGunAction.snipe";
	}
	
	/**
	 * Hit someone in this location if they are there and kill if unconscious 
	 * @param map - map
	 * @param x - the x for location
	 * @param y - the y for location
	 * @return
	 */
	private String shootLocation(GameMap map, int x, int y) {
		System.out.print("Blast goes to someone\n");
		Location loc;
		Actor target;
		String strIfDead = "";
		loc = new Location(map,x,y);
		if(loc.containsAnActor()&&rand.nextDouble()<0.75) {
			target = map.getActorAt(loc);
			target.hurt(shotgunDamage);
			if (!target.isConscious()) {
				kill(map);
				strIfDead = " dead ";
			}
			
			return target.toString() + " " +  strIfDead;
		}
		return "";
	}
	
	/**
	 * same as sniper but for shotgun, so instead shoot in a direction
	 * @param direction - the key with direciton option
	 * @param actor - player
	 * @param map - map
	 * @return
	 */
	private String blast(char direction, Actor actor, GameMap map) {
		for (Item item : actor.getInventory()) {
			if(item instanceof Shotgun) {
				((Shotgun) item).useAmmo();
				ammoLeft = ((Shotgun) item).getAmmo();
				break;
			}
		}
		String shotActors = "";
		Location origin = map.locationOf(actor);
		int ox = origin.x(); //original x
		int oy = origin.y(); //original y
		if (direction=='1') { //North
			for (int y=-1; y>=-3; y--) {
				for (int x=y; x<=-y; x++) {
					try {
						shotActors += shootLocation(map, ox+x, oy+y);
					}
					catch(ArrayIndexOutOfBoundsException exception) {
						continue;
					}
				}
			}
		}
		if (direction=='2') { //North-East
			for (int y=0; y>=-3; y--) {
				for (int x=0; x<=3; x++) {
					if (x!=0 && y!=0) {
						try {
							shotActors += shootLocation(map, ox+x, oy+y);
						}
						catch(ArrayIndexOutOfBoundsException exception) {
							continue;
						}
					}
				}
			}
		}
		if (direction=='3') { //East
			for (int x=1; x<=3; x++) {
				for (int y=-x; y<=x; y++) {
					try {
						shotActors += shootLocation(map, ox+x, oy+y);
					}
					catch(ArrayIndexOutOfBoundsException exception) {
						continue;
					}
				}
			}
		}
		if (direction=='4') { //South-East
			for (int y=0; y>=3; y++) {
				for (int x=0; x<=3; x++) {
					if (x!=0 && y!=0) {
						try {
							shotActors += shootLocation(map, ox+x, oy+y);
							}
						catch(ArrayIndexOutOfBoundsException exception) {
							continue;
						}
					}
				}
			}
		}
		if (direction=='5') { //South
			for (int y=1; y>=3; y++) {
				for (int x=-y; x<=y; x++) {
					try {
						shotActors += shootLocation(map, ox+x, oy+y);
					}
					catch(ArrayIndexOutOfBoundsException exception) {
						continue;
					}
				}
			}
		}
		if (direction=='6') { //South-West
			for (int y=0; y>=3; y++) {
				for (int x=0; x<=-3; x--) {
					if (x!=0 && y!=0) {
						try {
							shotActors += shootLocation(map, ox+x, oy+y);
						}
						catch(ArrayIndexOutOfBoundsException exception) {
							continue;
						}
					}
				}
			}
		}
		if (direction=='7') { //West
			for (int x=-1; x<=-3; x--) {
				for (int y=x; y<=-x; y++) {
					try {
						shotActors += shootLocation(map, ox+x, oy+y);
					}
					catch(ArrayIndexOutOfBoundsException exception) {
						continue;
					}
				}
			}
		}
		if (direction=='8') { //North-West
			for (int y=0; y>=-3; y--) {
				for (int x=0; x<=-3; x--) {
					if (x!=0 && y!=0) {
						try {
							shotActors += shootLocation(map, ox+x, oy+y);
						}
						catch(ArrayIndexOutOfBoundsException exception) {
							continue;
						}
					}
				}
			}
		}
		if (shotActors.isEmpty()) 
			return "Shotgun Blast has missed everyone. Shotgun Shells remaining: " + ammoLeft;
		else 
			return "Shotgun Blast hits: " + shotActors + " for " + shotgunDamage + ". Shotgun Shells remaining: " + ammoLeft;
	}
	
	/**
	 * get mode
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}
	
	
	@Override
	public String execute(Actor actor, GameMap map) {
		// break aim if last action wasn't UseGunAction with sniper mode 
		if (!(lastAction instanceof UseGunAction))
			((Player)actor).breakAim();
		else {
			if (((UseGunAction)lastAction).getMode()!="sniper")
				((Player)actor).breakAim();
		}
			
		
		if (mode == "shotgun") {
			return blast(showSubMenu(actor, map), actor, map);
		}
		if (mode == "sniper") {
			return snipe(showSubMenu(actor,map), actor, map);
		}
		return "error in UseGunAction.execute ";
	}

	@Override
	public String menuDescription(Actor actor) {
		if (mode=="shotgun") {
			return "Use Shotgun";
		}
		else if (mode=="sniper")
			return "Use Sniper";
		return "error in menuDescription in UseGunAction";
	}

}
