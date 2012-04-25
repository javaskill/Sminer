package org.infinitybots.bot.smithing;

import java.util.ArrayList;

import org.infinitybots.bot.mining.states.bank.BankCondition;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.bot.smithing.strategies.bank.BankTask;
import org.infinitybots.bot.smithing.strategies.forge.SmithCondition;
import org.infinitybots.bot.smithing.strategies.forge.SmithTask;
import org.infinitybots.bot.smithing.strategies.travel.TravelCondition;
import org.infinitybots.bot.smithing.strategies.travel.TravelTask;
import org.powerbot.concurrent.strategy.Strategy;

public class Smith {
	
	ArrayList<Strategy> strategies;
	
	public Smith(){
		strategies = new ArrayList<Strategy>();
	}
	public void setup(final String item){
		SmithSettings.item = SmithItem.get(item);
		strategies.add(new Strategy(new SmithCondition(),new SmithTask()));
		strategies.add(new Strategy(new TravelCondition(), new TravelTask()));
		strategies.add(new Strategy(new BankCondition(), new BankTask()));
	}
	public void setup(final String type, boolean cannonballs){
		
	}
	public void add(Strategy s){
		strategies.add(s);
	}
	public Strategy[] getStrategies(){
		return strategies.toArray(new Strategy[strategies.size()]);
	}
}