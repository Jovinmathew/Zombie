package game;

import edu.monash.fit2099.engine.GameMap;

public class MapChecker {
	
public boolean isCompound(GameMap map) {
	if(map.at(79,0).getDisplayChar()=='#')
	return false;
	
	return true;
	
}

}
