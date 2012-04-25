/**
 * 
 */
package org.infinitybots.bot;

import java.awt.Graphics;

import org.infinitybots.bot.gui.Gui;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.bot.event.listener.PaintListener;


/**
 * @author Brad
 *
 */
@Manifest(
        name = "Intelligent Sminer",
        authors = "javaskill",
        version = 1.0D,
        description = "Mines, Smiths, and Smelts.",
        premium = false)
public class InfinityMineAndSmith extends ActiveScript implements PaintListener {
	
	private PaintListener paint = null;
	private boolean setup = false;
	private Gui gui = null;
	
	@Override
	protected void setup() {
		try {
			gui = new Gui();
			gui.setVisible(true);
			new Thread(new Wait()).start();
		} catch(Exception e){e.printStackTrace();}
	}

	@Override
	public void onRepaint(Graphics g) {

	}
	class Wait implements Runnable {

		@Override
		public void run() {
			while(!gui.isDone()){
				Time.sleep(100);
			}
			for(Strategy s : gui.getStrategies()){
				provide(s);
			}
		}
	}
}