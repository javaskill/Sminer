package org.infinitybots.bot.smithing.strategies.travel;

import org.infinitybots.methods.Bank;
import org.powerbot.concurrent.Task;

/**
 * 
 * @author Brad
 *
 */
public class TravelTask implements Task {

	@Override
	public void run() {
		Bank.open();
	}

}