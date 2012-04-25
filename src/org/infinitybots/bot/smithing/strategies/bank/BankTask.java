package org.infinitybots.bot.smithing.strategies.bank;

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
	
	public BankTask(){
		it = SmithSettings.item;
	}
	@Override
	public void run() {
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
}