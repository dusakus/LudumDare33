package dcode.games.uEngine2.games.LD33.other;

import dcode.games.uEngine2.games.LD33.items.Item;

import java.util.LinkedList;

/**
 * Created by dusakus on 22.08.15.
 */
public class ItemQueueA {
	private Item[] itemlist = new Item[8];
	private LinkedList<Item> overload = new LinkedList<Item>();
	private boolean empty;

	public Item[] getList(int size){
		Item[] give = new Item[size];
		for (int i = 0; i < give.length; i++) {
			give[i] = itemlist[i];
		}
		return give;
	}

	public void tick(){
		for (int i = 0; i < itemlist.length; i++) {
			while (itemlist[i] == null) fetchitem(i);
		}
	}

	private void fetchitem(int i) {
		int j = i+1;
		while (itemlist[i] == null && i < itemlist.length){
			itemlist[i] = itemlist[j];
			j++;
			empty = false;
		}
		if(itemlist[i] == null && !overload.isEmpty()){
			itemlist[i] = overload.poll();
			empty = false;
		} else {
			empty = true;
		}
	}


}
