/**
 * 
 */
package org.infinitybots.bot.smithing.strategies.bank;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.Furnace;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.methods.Bank;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author Brad
 *
 */
public class BankCondition implements Condition {

	@Override
	public boolean validate() {
		if(SmithSettings.forge){
			final SmithItem item = SmithSettings.item;
			return (Inventory.getCount(item.getBarID()) < item.getRequiredBars() && Bank.isNear()) || Bank.isOpen();
		} else {
			final Bar bar = SmithSettings.type;
			return (bar.hasRequired() && Bank.isNear()) || Bank.isOpen();
		}
	}
}
