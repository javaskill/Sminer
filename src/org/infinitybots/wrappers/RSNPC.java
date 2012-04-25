package org.infinitybots.wrappers;

import java.awt.Point;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.NPC;

import org.infinitybots.input.*;
import org.infinitybots.methods.*;

/**
 * 
 * @author Brad
 * 
 * I like to do things my own way
 * 
 */
public class RSNPC {
	
	NPC npc;
	
	public RSNPC(NPC npc){
		this.npc = npc;
	}
	/**
	 * Hovers the object, and if direct is false human error will be introduced, 
	 * allowing the mouse to overshoot the object and then correct itself
	 * 
	 * @param direct
	 */
	public void hover(boolean direct){
		if(npc != null){
			if(isHovering()){
				if(Random.nextInt(0, 60) < 50)
					return;
			}
			MouseInput.move(getPoint(), direct);
		}
	}
	public boolean isHovering(){
		return npc.getModel().contains(Mouse.getLocation());
	}
	public void hover(){
		hover(Random.nextInt(0, 8) > 5);
	}
	public void click(boolean left){
		if(npc != null){
			if(isHovering()){
				if(Random.nextInt(0, 60) < 50){
					MouseInput.click(left);
					return;
				}
			}
			MouseInput.click(getPoint(), left, Random.nextInt(0, 8) > 5);
		}
	}
	public void interact(final String action){
		hover();
		RSMenu.doAction(action);
	}
	public Point getPoint(){
		if(npc != null)
		return npc.getCentralPoint();
		
		return null;
	}
}