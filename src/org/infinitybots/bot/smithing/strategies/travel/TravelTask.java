package org.infinitybots.bot.smithing.strategies.travel;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.Furnace;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.methods.Bank;
import org.powerbot.concurrent.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * 
 * @author Brad
 *
 */
public class TravelTask implements Task {

	@Override
	public void run() {
		if(SmithSettings.forge){
			Bank.open();
		} else {
			final Furnace furnace = SmithSettings.furnace;
			final Bar bar = SmithSettings.type;
			final boolean toBank = (furnace.isAt() && !bar.hasRequired()) ? true : false;
			if(toBank && Bank.isNear()){
				Bank.open();
			} else
			Walking.walk(furnace.getNext(!toBank));
		}
	}
}