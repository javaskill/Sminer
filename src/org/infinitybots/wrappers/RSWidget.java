package org.infinitybots.wrappers;

import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class RSWidget {
	
	final Widget widget;
	
	public RSWidget(final Widget widget){
		this.widget = widget;
	}
	public RSWidgetChild getChild(final int index){
		return new RSWidgetChild(widget.getChild(index));
	}
	public RSWidgetChild[] getChildren(){
		WidgetChild[] wca = widget.getChildren();
		RSWidgetChild[] rsca = new RSWidgetChild[wca.length];
		for(int i = 0;i < wca.length;i++){
			rsca[i] = new RSWidgetChild(wca[i]);
		}
		return rsca;
	}
	public boolean validate(){
		return widget.validate();
	}

}
