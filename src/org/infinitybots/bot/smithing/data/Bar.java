package org.infinitybots.bot.smithing.data;

/**
 * 
 * @author Brad
 *
 */
public enum Bar {
	
	BRONZE("Bronze bar", 2349),
	IRON("Iron bar", 2351),
	STEEL("Steel bar", 2353),
	MITHRIL("Mithril bar", 2359),
	ADAMANT("Adamant bar", 2361),
	RUNE("Rune bar", 2363);
	
	private final String name;
	private final int ID;
	
	Bar(final String name, final int ID){
		this.name = name;
		this.ID = ID;
	}
	public String getItemName(){
		return name;
	}
	public String getBarType(){
		return getItemName().split(" ")[0];
	}
	public int getID(){
		return ID;
	}
	public static Bar getType(final String type){
		for(Bar bar : Bar.values()){
			if(bar.getBarType().equalsIgnoreCase(type)){
				return bar;
			}
		}
		return null;
	}
}
