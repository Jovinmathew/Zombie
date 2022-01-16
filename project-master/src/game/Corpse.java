package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;

/**
 * Corpse which after 10 turns will become a zombie on an available location
 * 
 * @author ram
 *
 */
public class Corpse extends PortableItem{
	public int age = 0;
	public String name;
	
	public Corpse(String name) {
		super(name, '*');
		this.name = name;
	}
	
	/**
	 * the method to convert corpse to new zombie
	 * this is for when the item is on the ground
	 * @param location - the location of where the corpse 
	 */
	public void deadRise(Location location) {
		location.removeItem(this);
		Zombie newZombie = new Zombie(name);
		if(location.containsAnActor())
			for(Exit exit:location.getExits()) {
				Location destination = exit.getDestination();
				if (destination.canActorEnter(newZombie)) {
					destination.addActor(newZombie);
					break;
				}
			}
		else
			location.addActor(newZombie);
	}
	/**
	 * the method to convert cropse to new zombie
	 * this is for when the item is in the player's inventory
	 * @param location - the location of where the corpse 
	 */
	public void deadRise(Location location, Actor actor) {
		location.removeItem(this);
		Zombie newZombie = new Zombie(name);
		if(location.containsAnActor())
			for(Exit exit:location.getExits()) {
				Location destination = exit.getDestination();
				if (destination.canActorEnter(newZombie)) {
					destination.addActor(newZombie);
					break;
				}
			}
		else
			location.addActor(newZombie);
		actor.removeItemFromInventory(this);
	}
	
	/**
	 * tick for when the corpse is on the ground
	 * increments age of the corpse and calls dead rise if age limit is reached
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10) 
			deadRise(location);
	}
	
	/**
	 * tick for when the corpse is in the inventory of the player
	 * increments age of the corpse and calls dead rise if age limit is reached
	 */
	@Override
	public void tick(Location location, Actor actor) {
		super.tick(location);

		age++;
		if (age == 10) 
			deadRise(location, actor);
	}
	
}
