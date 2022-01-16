package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates an AttackAction if the current Actor is standing
 * next to an Actor that they can attack.
 * Altered in order to accomadate for zombieBiteAction and number of arms on the zombie
 * @author ram
 *
 */
public class AttackBehaviour implements Behaviour {
	private ZombieCapability attackableTeam;
	private Random rand = new Random();
	
	/**
	 * Constructor.
	 * 
	 * Sets the team (i.e. ZombieCapability) that the owner of this
	 * Behaviour is allowed to attack.
	 * 
	 * @param attackableTeam Team descriptor for ZombieActors that can be attacked
	 */
	public AttackBehaviour(ZombieCapability attackableTeam) {
		this.attackableTeam = attackableTeam;
	}

	/**
	 * Returns an AttackAction that attacks an adjacent attackable Actor.
	 * 
	 * Zombies are able to call zombie bite if the attackable team is Alive
	 * It also checks the numbers of arms in order to alter the chances of biting or punching
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		int arms;
		// Is there an attackable Actor next to me?
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().containsAnActor()))
				continue;
			if (attackableTeam == ZombieCapability.ALIVE) {
				arms = ((Zombie) actor).getArms();
				if (arms==0)
					if (e.getDestination().getActor().hasCapability(attackableTeam)) 
						return new ZombieBiteAction(e.getDestination().getActor());
				if (arms==1)
					if (rand.nextDouble()<0.75) {
						if (e.getDestination().getActor().hasCapability(attackableTeam)) {
							return new ZombieBiteAction(e.getDestination().getActor());
						}
					}
					else {
						if (e.getDestination().getActor().hasCapability(attackableTeam)) {
							return new AttackAction(e.getDestination().getActor());
						}
					}
				else {
					if (rand.nextDouble()<0.5) {
						if (e.getDestination().getActor().hasCapability(attackableTeam)) {
							return new ZombieBiteAction(e.getDestination().getActor());
						}
						
					}
					else {
						if (e.getDestination().getActor().hasCapability(attackableTeam)) {
							return new AttackAction(e.getDestination().getActor());
						}
					}
				}
			}
			if (rand.nextDouble()<0.5) {
				
			}
			if (e.getDestination().getActor().hasCapability(attackableTeam)) {
				return new AttackAction(e.getDestination().getActor());
			}
		}
		return null;
	}

}
