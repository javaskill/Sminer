package org.infinitybots.bot.smithing.strategies.smelt;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * 
 * @author Brad
 *
 */
public class SmeltCondition implements Condition {

	
	@Override
	public boolean validate() {
		Bar bar = SmithSettings.type;
		return bar.hasRequired() && Players.getLocal().getAnimation() == -1 && !Players.getLocal().isMoving();
	}

}