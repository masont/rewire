package edu.mit.rewire.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
	
	private List<Item> items;

	public DataSource() {
		this.items = new ArrayList<Item>();
	}
	
	public DataSource(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}

}
