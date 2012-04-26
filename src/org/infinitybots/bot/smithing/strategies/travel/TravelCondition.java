/**
 * 
 */
package org.infinitybots.bot.smithing.strategies.travel;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.Furnace;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.methods.Bank;
import org.powerbot.concurrent.strategy.*;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author Brad
 *
 */
public class TravelCondition implements Condition {

	@Override
	public boolean validate() {
		if(SmithSettings.forge){
			final SmithItem it = SmithSettings.item;
			return Inventory.getCount(it.getBarID()) < it.getRequiredBars() && !Players.getLocal().isMoving() && !Bank.isOpen();
		} else {
			final Furnace furnace = SmithSettings.furnace;
			final Bar bar = SmithSettings.type;
			return (furnace.isAt() && !bar.hasRequired()) || (furnace.isAtBank() && bar.hasRequired() && Inventory.getCount() == 28);
		}
	}
}