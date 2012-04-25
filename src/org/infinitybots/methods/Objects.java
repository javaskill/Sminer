package org.infinitybots.methods;

import org.powerbot.game.api.methods.node.*;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.*;

import org.infinitybots.wrappers.*;

public class Objects {
	
	public static RSObject getNearest(final int... id){
		SceneObject obj = SceneEntities.getNearest(new Filter<SceneObject>() {
            public boolean accept(final SceneObject loc) {
                    for (final int i : id) {
                            if (loc.getId() == i) {
                                    return true;
                            }
                    }
                    return false;
            }
		});
		if(obj != null){
			return new RSObject(obj);
		}
		return null;
	}
}
