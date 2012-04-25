/**
 * 
 */
package org.infinitybots.bot.mining.states.bank;

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
		SmithItem it = SmithSettings.item;
		return Bank.isOpen() || Inventory.getCount(it.getBarID()) < it.getRequiredBars();
	}

}
