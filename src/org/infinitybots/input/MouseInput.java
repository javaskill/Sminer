package org.infinitybots.input;

import java.awt.Point;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;

import org.infinitybots.intelligence.*;

/**
 * 
 * @author Brad
 *
 */
public class MouseInput {
	
	public static void mouseOvershoot(final Point point){
		mouseOvershoot(point.x, point.y,10,10);
	}
	public static void mouseOvershoot(final Point point, final int xrand, final int yrand){
		mouseOvershoot(point.x, point.y, xrand, yrand);
	}
	public static void move(final Point point){
		move(point.x,point.y);
	}
	public static void move(final int x, final int y){
		Mouse.move(x+Random.nextInt(-2, 2),y+Random.nextInt(-2, 2));
	}
	public static void move(Point point, boolean direct){
		if(direct){
			move(point);
		} else {
			mouseOvershoot(point);
		}
	}
	public static void click(Point point,boolean left, boolean direct){
		if(direct){
			move(point.x,point.y);
		} else {
			mouseOvershoot(point);
		}
		click(left);
	}
	public static void click(boolean left){
		Mouse.click(left);
	}
	/**
	 * Overshoots the mouse, will always correct itself.
	 * @param x The x location
	 * @param y The y location
	 * @param xRand The number of pixels to miss the location x by
	 * @param yRand The number of pixels to miss the location y by
	 */
	public static void mouseOvershoot(int x, int y,int xRand,int yRand){
		xRand = Random.nextInt(-xRand, xRand);
		yRand = Random.nextInt(-yRand, yRand);
		int xOverShoot = 0;
		int yOverShoot = 0;
		if(Random.nextInt(0,10) > 6){
			xOverShoot = x+xRand;
			yOverShoot = y+yRand;
			move(xOverShoot,yOverShoot);
		} else {
			xOverShoot = Random.nextInt(x-20,x+20);
			yOverShoot = Random.nextInt(y-20,y+20);
			move(xOverShoot,yOverShoot);
		}
		Methods.sleep(45,75);
		move(x+Random.nextInt(-4, 4),y+Random.nextInt(-4, 4));
	}
}