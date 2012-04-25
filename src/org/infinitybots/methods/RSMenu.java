package org.infinitybots.methods;

import java.awt.Point;

import org.powerbot.game.api.util.Random;

import org.infinitybots.intelligence.*;
import org.infinitybots.input.*;

public class RSMenu {
	
	public static void doAction(final String action){
		final int idx = getIndex(action);
		final String[] items = org.powerbot.game.api.methods.node.Menu.getItems();
		MouseInput.click(false);
		clickIndex(items, idx);
	}
	public static void doAction(final Point location,final String action){
		MouseInput.move(location, false);
		final int idx = getIndex(action);
		final String[] items = org.powerbot.game.api.methods.node.Menu.getItems();
		
		MouseInput.click(false);
		clickIndex(items, idx);
	}
	private static int getIndex(String action) {
		action = action.toLowerCase();
		final String[] items = org.powerbot.game.api.methods.node.Menu.getActions();
		for (int i = 0; i < items.length; i++) {
			if (items[i].toLowerCase().contains(action.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}
	private static void clickIndex(final String[] items, final int i) {
		final Point menu = org.powerbot.game.api.methods.node.Menu.getLocation();
		final int xOff = Random.nextInt(4, items[i].length() * 4);
		final int yOff = 21 + 16 * i + Random.nextInt(5, 14);
		MouseInput.mouseOvershoot(menu.x+xOff, menu.y+yOff, 3, 3);
		MouseInput.click(true);
		Methods.sleep(360,750);
	}
	public static String[] getOptions(){
		return org.powerbot.game.api.methods.node.Menu.getActions();
	}
}
