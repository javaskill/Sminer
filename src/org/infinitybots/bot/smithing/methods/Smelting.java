package org.infinitybots.bot.smithing.methods;

import org.infinitybots.bot.smithing.data.Bar;
import org.infinitybots.bot.smithing.settings.SmithSettings;
import org.infinitybots.methods.Interfaces;
import org.infinitybots.methods.Objects;
import org.infinitybots.wrappers.RSObject;
import org.infinitybots.wrappers.RSWidget;
import org.infinitybots.wrappers.RSWidgetChild;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;

public class Smelting {
	
	public static final int WIDGET_SMELT = 905;
	public static final int WIDGET_MAKE = 19;
	
	public static void open(){
		if(isOpen())
			return;
		RSObject obj = Objects.getNearest(SmithSettings.furnace.getID());
		if(obj != null){
			obj.interact("Smelt");
		}
	}
	public static void smelt(Bar bar){
		if(!isOpen()){
			open();
			Time.sleep(450,650);
			while(Players.getLocal().isMoving()){
				Time.sleep(250);
			}
		}
		if(isOpen()){
			RSWidgetChild makeButton = getRSWidget().getChild(WIDGET_MAKE);
			makeButton.click(true);
			Time.sleep(650,850);
		}
	}
	public static boolean isOpen(){
		return getRSWidget().validate();
	}
	public static RSWidget getRSWidget(){
		return Interfaces.getWidget(WIDGET_SMELT);
	}
}
