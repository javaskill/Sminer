/**
 * 
 */
package org.infinitybots.bot.smithing.methods;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.data.SmithItem;
import org.infinitybots.methods.Interfaces;
import org.infinitybots.methods.Objects;
import org.infinitybots.wrappers.RSObject;
import org.infinitybots.wrappers.RSWidget;
import org.infinitybots.wrappers.RSWidgetChild;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.widget.*;

/**
 * @author Brad
 *
 */
public class Smithing {
	
	public static final int[] HAMMERS = { 2347 };
	public static final int[] ANVILS = { 2783 };
	
	public static final int WIDGET_SMITHING = 300;
	public static final int WIDGET_SCROLL_BAR =  16;
	public static final int WIDGET_SMITHING_CLOSE = 13;
	
	public static boolean open(){
		RSObject obj = Objects.getNearest(ANVILS);
		if(!obj.isOnScreen()){
			Walking.walk(obj.getSceneObject());
			
		}
		if(obj.isOnScreen()){
			obj.click(true);
			waitForMovement();
		}
		Time.sleep(900, 1250);
		return isOpen();
	}
	private static void waitForMovement(){
		Time.sleep(350,400);
		while(Players.getLocal().isMoving()){
			Time.sleep(100);
		}
	}
	public static boolean make(final SmithItem si, final int count){
		if(isOpen()){
			RSWidgetChild wc = null;
			for(RSWidgetChild c : getWidget().getChildren()){
				if(c.getChildId() == si.getID()){
					wc = getWidget().getChild(c.getIndex()-1);
					break;
				}
			}
			if(wc == null)
				return false;
			if (!Interfaces.scrollTo(wc, Interfaces.getComponent((WIDGET_SMITHING << 16) + WIDGET_SCROLL_BAR))) {
				return false;
			}
			String action = "Make All";
			switch(count){
			case 0:
				break;
			case 1:
				return wc.click(true);
			case 5:
				action = "Make 5";
				break;
			case 10:
				action = "Make 10";
				break;
			default:
				int i = -1;
				try {
					i = Integer.parseInt(wc.getActions()[3].toLowerCase().trim().replaceAll("\\D", ""));
					if(i == count){
						action = "Make "+count;
					} else {
						return wc.interact("Make X");
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			boolean ret = wc.interact(action);
			Time.sleep(400,700);
			return ret;
		}
		return false;
	}
	public static void close(){
		getWidget().getChild(WIDGET_SMITHING_CLOSE).click(true);
	}
	public static boolean isOpen(){
		return getWidget().validate();
	}
	public static RSWidget getWidget(){
		return Interfaces.getWidget(WIDGET_SMITHING);
	}

}
