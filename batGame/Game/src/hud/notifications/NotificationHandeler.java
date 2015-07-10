package hud.notifications;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class NotificationHandeler {
	private LinkedList<Notification> notifications;
	private Notification currentNotification;
	private Skin skin;
	
	public NotificationHandeler(Skin skin) {
		this.skin = skin;
		notifications = new LinkedList<Notification>();
	}
	
	public void update(Stage stage) {
		if(currentNotification == null) {
			if(notifications.size() > 0) {
				currentNotification = notifications.getFirst();
				Gdx.app.log("Notifications", "add notification");
				stage.addActor(currentNotification);
			}			
		} else {
			if(currentNotification.getActions().size == 0) {
				notifications.removeFirst();
				currentNotification.remove();
				currentNotification = null;
				Gdx.app.log("Notifications", "remove notification");
			}
		}
	}
	
	public void addFadeNotification(String message, String styleName, float x, float y,
			float fadeInTime, float fadeOutTime, float screenTime) {
		
		Notification notification = new Notification (message, skin, styleName);
		
		notification.setX(x);
		notification.setY(y);
		
		Color old = notification.getColor();
		notification.setColor(old.a, old.g, old.b, 0);
		
		Action fadeIn = Actions.fadeIn(fadeInTime);
		Action fadeOut = Actions.fadeOut(fadeOutTime);
		Action delay = Actions.delay(screenTime, fadeOut);
		Action sequence = Actions.sequence(fadeIn, delay);
		
		notification.addAction(sequence);

		notifications.addLast(notification);
	}
	
	public void addSlideNotification(String message, String styleName, 
			float startingX, float startingY, float endX, float endY,
			float fadeInTime, float fadeOutTime, float screenTime) {
		
		Notification notification = new Notification(message, skin, styleName);
		
		notification.setX(startingX);
		notification.setY(startingY);
				
		Action moveIn = Actions.moveTo(endX, endX, fadeInTime);
		Action moveOut = Actions.moveTo(startingX, startingY, fadeOutTime);
		Action delay = Actions.delay(screenTime, moveOut);
		Action sequence = Actions.sequence(moveIn, delay);
		
		notification.addAction(sequence);

		notifications.addLast(notification);
	}
}
