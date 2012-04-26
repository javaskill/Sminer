package org.infinitybots.bot.smithing.data;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;

/**
 * 
 * @author Brad
 *
 */
public enum Bar {
	
	BRONZE("Bronze bar", 2349, true, Ore.COPPER, Ore.TIN),
	IRON("Iron bar", 2351, false, Ore.IRON),
	STEEL("Steel bar", 2353, true, Ore.IRON, Ore.COAL, Ore.COAL),
	GOLD("Gold bar", 2357, false, Ore.GOLD),
	MITHRIL("Mithril bar", 2359, true, Ore.MITHRIL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL),
	ADAMANT("Adamant bar", 2361, true, Ore.ADAMANTITE, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL),
	RUNE("Rune bar", 2363, true, Ore.RUNITE, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL, Ore.COAL);
	
	private final Ore[] required;
	
	private final boolean hasSecondary;
	private final String name;
	private final int ID;
	
	Bar(final String name, final int ID, final boolean hasSeconday,Ore... required){
		this.required = required;
		this.hasSecondary = hasSeconday;
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
	public boolean hasRequired(){ 
		try {
		final int primary = required[0].getID();
		if(Inventory.getCount(primary) > 0){
			if(!hasSecondary()){
				return true;
			}
			final int secondary = required[1].getID();
			return Inventory.getCount(secondary) >= required.length-1;
		}
		} catch(Exception e) {e.printStackTrace();Time.sleep(5000);}
		return false;
	}
	public Ore getPrimary(){
		return required[0];
	}
	public boolean hasSecondary(){
		return hasSecondary;
	}
	public Ore getSecondary(){
		if(hasSecondary())
		return required[1];
		return null;
	}
	public int getRequiredPrimaryCount(){
		return roundDown((required.length-1)/ 28);
	}
	public int roundDown(final double i){
		return (int)(i/1000 * 1000);
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
