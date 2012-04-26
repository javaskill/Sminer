package org.infinitybots.bot.smithing.strategies.bank;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.Ore;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.methods.Bank;
import org.powerbot.concurrent.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;

/**
 * 
 * @author Brad
 *
 */
public class BankTask implements Task {

	
	SmithItem it;
	Bar bar;
	
	public BankTask(){
		it = SmithSettings.item;
		bar = SmithSettings.type;
	}
	@Override
	public void run() {
		if(SmithSettings.forge)
			ForgeBankTask();
		else
			SmeltBankTask();
	}
	public void ForgeBankTask(){
		if(!Bank.isOpen()){
			Bank.open();
		} else {
			int count = Inventory.getCount();
			if(count > 0){
				if(Inventory.getCount(it.getBarID()) != count){
					Bank.depositAll();
					Time.sleep(450,750);
				}
			}
			int c = Inventory.getCount();
			if(c == 0 || c == Inventory.getCount(it.getID())){
				Bank.withdraw(it.getBarID(), 0);
				Time.sleep(350,450);
			}
			if(Inventory.getCount(it.getBarID()) == 28){
				Bank.close();
			}
		}
	}
	public void SmeltBankTask(){
		System.out.println("Smelt Bank Task.");
		if(!Bank.isOpen()){
			Bank.open();
		} else {
			int count = Inventory.getCount();
			if(bar.hasRequired() && count == 28){
				Bank.close();
				return;
			} else if (count > 0 && !bar.hasRequired()){
				Bank.depositAll();
				Time.sleep(350,600);
			}
			if(Inventory.getCount() == 0){
				Ore primary = bar.getPrimary();
				Bank.withdraw(primary.getID(), bar.getRequiredPrimaryCount());
				if(bar.hasSecondary()){
					Bank.withdraw(bar.getSecondary().getID(), 0);
				}
			}
		}
	}
}