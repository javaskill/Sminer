package org.infinitybots.bot.smithing;

import java.util.ArrayList;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.Furnace;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.bot.smithing.strategies.bank.BankCondition;
import org.infinitybots.bot.smithing.strategies.bank.BankTask;
import org.infinitybots.bot.smithing.strategies.forge.SmithCondition;
import org.infinitybots.bot.smithing.strategies.forge.SmithTask;
import org.infinitybots.bot.smithing.strategies.smelt.SmeltCondition;
import org.infinitybots.bot.smithing.strategies.smelt.SmeltTask;
import org.infinitybots.bot.smithing.strategies.travel.TravelCondition;
import org.infinitybots.bot.smithing.strategies.travel.TravelTask;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;

public class Smith {
	
	ArrayList<Strategy> strategies;
	
	public Smith(){
		strategies = new ArrayList<Strategy>();
	}
	public void setup(final String item){
		SmithSettings.forge = true;
		SmithSettings.item = SmithItem.get(item);
		
		add(new SmithCondition(),new SmithTask());
		add(new TravelCondition(), new TravelTask());
		add(new BankCondition(), new BankTask());
	}
	public void setup(final String location, final String type){
		SmithSettings.forge = false;
		SmithSettings.furnace = Furnace.get(location);
		SmithSettings.type = Bar.getType(type);
		
		add(new SmeltCondition(), new SmeltTask());
		add(new TravelCondition(), new TravelTask());
		add(new BankCondition(), new BankTask());
	}
	public void add(final Condition condition, final Task task){
		strategies.add(new Strategy(condition,task));
	}
	public Strategy[] getStrategies(){
		return strategies.toArray(new Strategy[strategies.size()]);
	}
}