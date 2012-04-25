/**
 * 
 */
package org.infinitybots.bot.smithing.strategies.forge;

import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author Brad
 *
 */
public class SmithCondition implements Condition {

	@Override
	public boolean validate() {
		SmithItem item = SmithSettings.item;
		return Inventory.getCount(item.getBarID()) >= item.getRequiredBars() && !Players.getLocal().isMoving();
	}
}
