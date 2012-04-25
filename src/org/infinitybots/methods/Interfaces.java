package org.infinitybots.methods;

import org.infinitybots.wrappers.RSWidget;
import org.infinitybots.wrappers.RSWidgetChild;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

public class Interfaces {
	
	
	public static boolean scrollTo(final RSWidgetChild component, final RSWidgetChild scrollBar) {
		if (component == null || scrollBar == null || !component.validate()) {
			return false;
		}
		if (scrollBar.getChildren().length != 6) {
			return true;
		}

		RSWidgetChild scrollableArea = component;
		while (scrollableArea.getScrollableContentHeight() == 0
				&& scrollableArea.getParentId() != -1) {
			scrollableArea = getComponent(scrollableArea.getParentId());
		}
		if (scrollableArea.getScrollableContentHeight() == 0) {
			return false;
		}
		final int areaY = scrollableArea.getAbsoluteY();
		final int areaHeight = scrollableArea.getHeight();

		if (component.getAbsoluteY() >= areaY
				&& component.getAbsoluteY() <= areaY + areaHeight
				- component.getHeight()) {
			return true;
		}
		final RSWidgetChild scrollBarArea = scrollBar.getChildren()[0];
		final int contentHeight = scrollableArea.getScrollableContentHeight();

		int pos = (int) ((float) scrollBarArea.getHeight() / contentHeight * (component.getRelativeY() + Random.nextInt(-areaHeight / 2, areaHeight / 2 - component.getHeight())));
		if (pos < 0)
		{
			pos = 0;
		} else if (pos >= scrollBarArea.getHeight()) {
			pos = scrollBarArea.getHeight() - 1;
		}
		Mouse.click(scrollBarArea.getAbsoluteX() + Random.nextInt(0, scrollBarArea.getWidth()), scrollBarArea.getAbsoluteY() + pos, true);
		Time.sleep(200, 400);
		while (component.getAbsoluteY() < areaY || component.getAbsoluteY() > areaY + areaHeight - component.getHeight()) {
			final boolean scrollUp = component.getAbsoluteY() < areaY;
			scrollBar.getChildren()[(scrollUp ? 4 : 5)].interact("");
			Time.sleep(100, 200);
		}
		return component.getAbsoluteY() >= areaY && component.getAbsoluteY() <= areaY + areaHeight - component.getHeight();
	}
	public static RSWidgetChild getComponent(final int id) {
		final int x = id >> 16;
		final int y = id & 0xFFFF;
		return new RSWidgetChild(Widgets.get(x).getChild(y));
	}
	public static RSWidget getWidget(final int ID){
		return new RSWidget(Widgets.get(ID));
	}
}
