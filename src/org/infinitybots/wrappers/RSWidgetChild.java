package org.infinitybots.wrappers;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.infinitybots.input.MouseInput;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class RSWidgetChild {
	
	final WidgetChild widgetchild;
	
	public RSWidgetChild(final WidgetChild widgetchild){
		this.widgetchild = widgetchild;
	}
	public RSWidgetChild getChild(int idx){
		return new RSWidgetChild(widgetchild.getChild(idx));
	}
	public WidgetChild getWidgetChild(){
		return widgetchild;
	}
	public RSWidgetChild[] getChildren(){
		WidgetChild[] wca = widgetchild.getChildren();
		RSWidgetChild[] rsca = new RSWidgetChild[wca.length];
		for(int i = 0;i < wca.length;i++){
			rsca[i] = new RSWidgetChild(wca[i]);
		}
		return rsca;
	}
	public int getIndex(){
		return widgetchild.getIndex();
	}
	
	public String[] getActions(){
		return widgetchild.getActions();
	}
	public boolean validate(){
		return widgetchild.validate();
	}
	public int getChildId(){
		return widgetchild.getChildId();
	}
	public boolean click(boolean left){
		MouseInput.click(getLocation(), left, Random.nextInt(0,100) < 80);
		return widgetchild.isOnScreen();
	}
	public void hover(){
		MouseInput.mouseOvershoot(getLocation());
	}
	public boolean interact(final String action){
		return widgetchild.interact(action);
	}
	/**
	 * Picks a random location
	 * 
	 * @return A location in the widget bounds
	 */
	public Point getLocation(){
		int x = widgetchild.getAbsoluteX();
		int y = widgetchild.getAbsoluteY();
		int rx = Random.nextInt(0, widgetchild.getWidth());
		int ry = Random.nextInt(0, widgetchild.getHeight());
		return new Point(x+rx,y+ry);
	}
	
	//
	
	public RSWidgetChild getParent() {
		return new RSWidgetChild(widgetchild.getParent());
	}

	public int getAbsoluteX() {
		return getAbsoluteLocation().x;
	}

	public int getAbsoluteY() {
		return getAbsoluteLocation().y;
	}

	public Point getAbsoluteLocation() {
		return widgetchild.getAbsoluteLocation();
	}

	public int getRelativeX() {
		return widgetchild.getRelativeX();
	}

	public int getRelativeY() {
		return widgetchild.getRelativeY();
	}

	public Point getRelativeLocation() {
		return widgetchild.getRelativeLocation();
	}

	public int getWidth() {
		return widgetchild.getWidth();
	}

	public int getHeight() {
		return widgetchild.getHeight();
	}

	public int getId() {
		return widgetchild.getId();
	}

	public int getChildIndex() {
		return widgetchild.getChildIndex();
	}

	public String getChildName() {
		return widgetchild.getChildName();
	}

	public String getText() {
		return widgetchild.getText();
	}

	public int getChildStackSize() {
		return widgetchild.getChildStackSize();
	}

	public boolean isHorizontallyFlipped() {
		return widgetchild.isHorizontallyFlipped();
	}

	public boolean isVerticallyFlipped() {
		return widgetchild.isVerticallyFlipped();
	}

	public int getHorizontalScrollPosition() {
		return widgetchild.getHorizontalScrollPosition();
	}

	public int getScrollableContentWidth() {
		return widgetchild.getScrollableContentWidth();
	}

	public int getHorizontalScrollThumbSize() {
		return widgetchild.getHorizontalScrollThumbSize();
	}

	public int getVerticalScrollPosition() {
		return widgetchild.getVerticalScrollPosition();
	}

	public int getScrollableContentHeight() {
		return widgetchild.getScrollableContentHeight();
	}

	public int getVerticalScrollThumbSize() {
		return getVerticalScrollThumbSize();
	}

	public int getBoundsArrayIndex() {
		return widgetchild.getBoundsArrayIndex();
	}
	public boolean isVisible() {
		return widgetchild.isVisible();
	}

	@Override
	public int hashCode() {
		return widgetchild.hashCode();
	}
	@Override
	public boolean equals(final Object obj) {
		return widgetchild.equals(obj);
	}
	public int getParentId() {
		return widgetchild.getParentId();
	}

	public boolean isInScrollableArea() {
		return widgetchild.isInScrollableArea();
	}
	public boolean isOnScreen() {
		return widgetchild.isOnScreen();
	}

	public Polygon[] getBounds() {
		return widgetchild.getBounds();
	}

	public Rectangle getBoundingRectangle() {
		return widgetchild.getBoundingRectangle();
	}
	
	//
}
