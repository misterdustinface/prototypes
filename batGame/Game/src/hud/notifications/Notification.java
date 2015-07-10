package hud.notifications;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Notification extends Label {
	public Notification(String message, Skin skin, String style) {
		super(message, skin, style);
		this.setWrap(true);
	}
	
	public boolean isNotificationFinished() {
		return getActions().size == 0;
	}
}
