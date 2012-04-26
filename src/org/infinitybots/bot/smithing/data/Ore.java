package org.infinitybots.bot.smithing.data;

/**
 * 
 * @author Brad
 *
 */
public enum Ore {
	
	COPPER("Copper ore", 436),
	TIN("Tin ore", 438),
	COAL("Coal", 453),
	IRON("Iron ore", 440),
	STEEL("Steel ore", -1),
	GOLD("Gold ore", 444),
	MITHRIL("Mithril ore", 447),
	ADAMANTITE("Adamantite ore", 449),
	RUNITE("Runite ore", 451);
	
	final String name;
	final int ID;
	
	Ore(final String name, final int ID){
		this.name = name;
		this.ID = ID;
	}
	public String getName(){
		return name;
	}
	public int getID(){
		return ID;
	}

}
