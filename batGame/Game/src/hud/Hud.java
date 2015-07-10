package hud;

import lua.LuaScriptManager;
import hud.debug.ConsoleOutput;
import hud.notifications.NotificationHandeler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * import com.badlogic.gdx.scenes.scene2d.ui.*
 * 
 * Button
 * ButtonGroup
 * CheckBox
 * Dialog
 * HorizontalGroup
 * Image
 * ImageButton
 * ImageTextButton
 * Label
 * List
 * ScrollPane
 * SelectBox
 * Skin
 * Slider
 * SplitPane
 * Stack
 * Table
 * TableToolkit
 * TextButton
 * TextField
 * Touchpad
 * Tree
 * VerticalGroup
 * Widget
 * WidgetGroup
 * Window
 */

public class Hud {
	public static Skin skin;
	protected static Stage stage;
	private static SpriteBatch batch;
	private static NotificationHandeler notifications;
	private static ConsoleOutput out;
	private static OrthographicCamera cam;
	
	public static void init(LuaScriptManager lua) {
		skin = new Skin();
		stage = new Stage(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
		batch = new SpriteBatch();
		notifications = new NotificationHandeler(skin);
		
		intitTest(lua);
	}
	
	public static void addFadeNotification(String message) {
		notifications.addFadeNotification(message, Constants.FADE_NOTE_STYLE, Constants.FADE_NOTE_X,
				Constants.FADE_NOTE_Y, Constants.FADE_NOTE_IN_TIME, Constants.FADE_NOTE_OUT_TIME,
				Constants.FADE_NOTE_SCREEN_TIME);
	}
		
	
	public static void addSlideNotification(String message) {
		notifications.addSlideNotification(message, Constants.SLIDE_NOTE_STYLE, Constants.SLIDE_NOTE_STARTING_X,
				Constants.SLIDE_NOTE_STARTING_Y, Constants.SLIDE_NOTE_ENDING_X, Constants.SLIDE_NOTE_ENDING_Y,
				Constants.SLIDE_NOTE_IN_TIME, Constants.SLIDE_NOTE_OUT_TIME, Constants.SLIDE_NOTE_SCREEN_TIME);
	}
	
	public static void update() {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		notifications.update(stage);
		out.update();
	}
	
	public static void render() {
		stage.draw();
	}
	
	public static void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}
	
	public static void dispose() {
		skin.dispose();
		stage.dispose();
		batch.dispose();
	}
	
	public static void intitTest(final LuaScriptManager lua) {
		FreeTypeFontGenerator testGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/molten.ttf"));

		BitmapFont font12 = testGen.generateFont(25);
		testGen.dispose(); 
		
		skin.add("molten_12", font12);
		font12 = new BitmapFont();
		skin.add("Arial", font12);

		
		// Generate a 1x1 white texture and store it in the hudTest.skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
	
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.background = skin.newDrawable("white", new Color(0.75f,0.75f,0.75f,0f));
		labelStyle.fontColor = Color.MAGENTA;
		labelStyle.font = skin.getFont("Arial");
		skin.add("default", labelStyle);
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.background = skin.newDrawable("white", new Color(0.75f,0.75f,0.75f,0.2f));
		textFieldStyle.focusedBackground = skin.newDrawable("white", new Color(0.75f,0.75f,0.75f,0.5f));
		textFieldStyle.cursor = skin.newDrawable("white", new Color(1.0f,0.0f,1.0f,1.0f));

		textFieldStyle.fontColor = Color.MAGENTA;
		textFieldStyle.font = skin.getFont("Arial");
		skin.add("default", textFieldStyle);
		
		
		TextField t = new TextField("", skin);
		t.setY(Constants.STAGE_HEIGHT - 30);
		t.setWidth(Constants.STAGE_WIDTH);
		t.setTextFieldListener(new TextFieldListener(){
			static final int ENTER_KEY = 13;
			@Override
			public void keyTyped(TextField textField, char key) {
				if(key == (char)ENTER_KEY) {
					String text = textField.getText();
					textField.setText("");
					Gdx.app.log("Attempt to compile lua", text);
					try {
						lua.executeString(text);
					} catch (Exception ex) {
						Gdx.app.log("Error compiling ", text);
					}
				}
			}
		});
				
		stage.addActor(t);
		
		out = new ConsoleOutput(skin, 5);
		out.setWidth(Constants.STAGE_WIDTH);
		
		ScrollPane scroll = new ScrollPane(out);
		scroll.setWidth(Constants.STAGE_WIDTH);
		scroll.setHeight(100);
		stage.addActor(scroll);
	}
	
	public static InputProcessor getInputProcessor() {
		return stage;
	}
}
