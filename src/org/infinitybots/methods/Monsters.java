package org.infinitybots.methods;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;

import org.infinitybots.wrappers.*;

/**
 * 
 * @author Brad
 *
 * I like doing things my own way
 */
public class Monsters {
	
	public static RSNPC getNearest(final int... ids){
		NPC monster = NPCs.getNearest(ids);
		if(monster != null)
		return new RSNPC(monster);
		return null;
	}
}