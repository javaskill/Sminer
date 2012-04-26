package org.infinitybots.bot.smithing.data;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;

public enum Furnace {
	
	AL_KHARID("Al Kharid", new TilePath(new Tile[] { new Tile(3270,3167,0), new Tile(3277, 3178, 0), new Tile(3275, 3186, 0) }),
			new Tile(3275, 3186, 0), new Tile(3270,3167,0), 11666);
	final TilePath toFurnace;
	final TilePath toBank;
	
	final String location;
	final Tile tile;
	final Tile bankTile;
	final int ID;
	
	Furnace(final String location, final TilePath toFurnace, final Tile tile,final Tile bankTile, final int ID){
		this.toFurnace = toFurnace;
		toBank = toFurnace.reverse();
		this.location = location;
		this.tile = tile;
		this.bankTile = bankTile;
		this.ID = ID;
	}
	public String getLocation(){
		return location;
	}
	public Tile getTile(){
		return tile;
	}
	public Tile getBankTile(){
		return bankTile;
	}
	public int getID(){
		return ID;
	}
	public boolean isAt(){
		return Calculations.distanceTo(getTile()) < 8;
	}
	public boolean isAtBank(){
		return Calculations.distanceTo(getBankTile()) < 8;
	}
	public static Furnace get(final String location){
		for(Furnace f : Furnace.values()){
			if(f.getLocation().equalsIgnoreCase(location)){
				return f;
			}
		}
		return null;
	}
	/**
	 * The next Tile in the direction of the bank or furnace
	 * 
	 * @return Tile tile location
	 */
	public Tile getNext(boolean tofurn){
		if(tofurn){
			return toFurnace.getNext();
		} else {
			return toBank.getNext();
		}
	}
}
