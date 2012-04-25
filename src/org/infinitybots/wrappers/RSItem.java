package org.infinitybots.wrappers;

import java.awt.Point;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.*;

import org.infinitybots.input.*;
import org.infinitybots.methods.*;

/**
 * 
 * @author Brad
 *
 * I like doing things my own way
 */
public class RSItem {
	
	final Item item;
	
	public RSItem(final Item item){
		this.item = item;
	}
	/**
	 * 
	 * @return The center point of the inv location
	 */
	public Point getCentralPoint(){
		int x = item.getWidgetChild().getAbsoluteX();
		int y = item.getWidgetChild().getAbsoluteY();
		int w = item.getWidgetChild().getWidth()/2;
		int h = item.getWidgetChild().getHeight()/2;
		return new Point(x+w,y+h);
	}
	public int getStackSize(){
		return item.getStackSize();
	}
	public RSWidgetChild getWidgetChild(){
		return new RSWidgetChild(item.getWidgetChild());
	}
	public void hover(){
		hover(Random.nextInt(0, 8) > 5);
	}
	public void hover(boolean direct){
		if(isHovering()){
			if(Random.nextInt(0, 60) < 50)
				return;
		}
		MouseInput.move(getCentralPoint(), direct);
	}
	public boolean isHovering(){
		return item.getWidgetChild().contains(Mouse.getLocation());
	}
	public void click(final boolean left){
		if(isHovering()){
			if(Random.nextInt(0, 60) < 50){
				MouseInput.click(left);
				return;
			}
		}
		MouseInput.click(getCentralPoint(), left, Random.nextInt(0, 8) > 5);
	}
	public void click(final boolean left, final boolean direct){
		if(isHovering()){
			if(Random.nextInt(0, 60) < 50){
				MouseInput.click(left);
				return;
			}
		}
		MouseInput.click(getCentralPoint(), left, direct);
	}
	public int getID(){
		return item.getId();
	}
	public String getName(){
		return item.getName();
	}
	public void interact(final String action){
		hover();
		RSMenu.doAction(action);
	}
}