package org.infinitybots.intelligence;

import org.powerbot.game.api.util.Random;

public class Methods {
	
	public static void sleep(final int min, final int max){
		try {
			Thread.sleep(Random.nextInt(min, max));
		} catch(Exception e){}
	}

}
