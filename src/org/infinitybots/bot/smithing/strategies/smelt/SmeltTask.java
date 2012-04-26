package org.infinitybots.bot.smithing.strategies.smelt;

import org.infinitybots.bot.smithing.methods.Smelting;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.powerbot.concurrent.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;

/**
 * 
 * @author Brad
 *
 */
public class SmeltTask implements Task {

	long lastTask = 0;
	
	@Override
	public void run() {
		
		Smelting.open();
		Time.sleep(200,400);
		Smelting.smelt(SmithSettings.type);
		lastTask = System.currentTimeMillis();
	}
}
