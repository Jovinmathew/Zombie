package game;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class ListItems {
	private ArrayList<Item> everyItem = new ArrayList<Item>();
	private GameMap map;
	
	public ListItems(GameMap map) {
		this.map = map;
	}
	
	public ArrayList<Item> getItems() {
		
		for (int x=0; x<80; x++) {
			for (int y=0; y<25; y++) {

				List <Item> items = map.at(x, y).getItems();
				if (!items.isEmpty()) {
					for(int i=0;i<items.size();i++) {
						Item refItem=items.get(i);
						everyItem.add(refItem);
					}
				}
			}
		}
		return everyItem;
	}
}
