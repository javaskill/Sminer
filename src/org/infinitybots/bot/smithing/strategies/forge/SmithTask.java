package org.infinitybots.bot.smithing.strategies.forge;

import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.methods.Smithing;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.powerbot.concurrent.Task;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * 
 * @author Brad
 *
 */
public class SmithTask implements Task {

	SmithItem item;
	long lastTime = 0;
	
	public SmithTask(){
		item = SmithSettings.item;
	}
	@Override
	public void run() {
		if(!Smithing.isOpen() && Players.getLocal().getAnimation() == -1 && System.currentTimeMillis() - lastTime > 10000){
			Smithing.open();
		} else {
			Smithing.make(item, 0);
			lastTime = System.currentTimeMillis();
		}
	}
}
