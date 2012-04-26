package org.infinitybots.bot.smithing.data;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;

public enum Furnace {
	
	AL_KHARID("Al Kharid", new Tile(3275, 3186, 0), new Tile(3270,3167,0), 11666);
	
	final String location;
	final Tile tile;
	final Tile bankTile;
	final int ID;
	
	Furnace(final String location, final Tile tile,final Tile bankTile, final int ID){
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
			final Tile tile = getTile();
			final Tile bankTile = getBankTile();
			if(Calculations.distanceTo(tile) < 14){ // On screen
				return tile;
			} else if(Calculations.distanceTo(bankTile) < 14){
				final Tile plr = Players.getLocal().getLocation();
				final int diffY = tile.getY() - bankTile.getY(), diffX = tile.getX() - bankTile.getX();
				final int toX = plr.getX() + (diffX/2), toY = plr.getY()+(diffY/2);
				return new Tile(toX+Random.nextInt(-3, 3), toY+Random.nextInt(-3, 3), 0);
			} else {
				System.out.println("We are not near "+getLocation() +" furnace.");
			}
		} else {
			final Tile tile = getTile();
			final Tile bankTile = getBankTile();
			if(Calculations.distanceTo(bankTile) < 14){ // On screen
				return bankTile;
			} else if(Calculations.distanceTo(tile) < 14){
				final Tile plr = Players.getLocal().getLocation();
				final int diffY = bankTile.getY() - tile.getY(), diffX = bankTile.getX() - tile.getX();
				final int toX = plr.getX() + (diffX/2), toY = plr.getY()+(diffY/2);
				return new Tile(toX+Random.nextInt(-3, 3), toY+Random.nextInt(-3, 3), 0);
			} else {
				System.out.println("We are not near "+getLocation() + " bank.");
			}
		}
		return null;
	}
}
