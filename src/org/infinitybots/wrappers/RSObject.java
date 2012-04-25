package org.infinitybots.wrappers;

import java.awt.Point;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;

import org.infinitybots.input.*;
import org.infinitybots.methods.*;

/**
 * 
 * @author Brad
 * 
 * I like to do things my own way
 * 
 */
public class RSObject {
	
	SceneObject obj;
	
	public RSObject(SceneObject obj){
		this.obj = obj;
	}
	/**
	 * Hovers the object, and if direct is false human error will be introduced, 
	 * allowing the mouse to overshoot the object and then correct itself
	 * 
	 * @param direct
	 */
	public void hover(final boolean direct){
		if(obj != null){
			if(isHovering()){
				if(Random.nextInt(0, 60) < 50){
					return;
				}
			}
			MouseInput.move(getPoint(), direct);
		}
	}
	public void hover(){
		hover(Random.nextInt(0, 8) > 5);
	}
	public boolean isHovering(){
		return obj.getModel().contains(Mouse.getLocation());
	}
	public void click(final boolean left){
		if(obj != null) {
			if(isHovering()){
				if(Random.nextInt(0, 60) < 50){
					MouseInput.click(Random.nextInt(0, 8) > 5);
					return;
				}
			}
			MouseInput.click(getPoint(), left, false);
		}
	}
	public boolean isOnScreen(){
		return obj.isOnScreen();
	}
	public SceneObject getSceneObject(){
		return obj;
	}
	public void interact(final String action){
		hover();
		RSMenu.doAction(action);
	}
	public Point getPoint(){
		if(obj != null)
		return obj.getCentralPoint();
		return null;
	}
}