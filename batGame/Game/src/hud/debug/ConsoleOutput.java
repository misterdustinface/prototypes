package hud.debug;

import java.io.IOException;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import debug.PipeStandardOut;

public class ConsoleOutput extends Label {
	private LinkedList<String> lines;
	private int linesDisplayed;

	public ConsoleOutput(Skin skin, int linesDisplayed) {
		super("", skin);
		this.setWrap(true);
		this.linesDisplayed = linesDisplayed;
		lines = new LinkedList<String>();
		
		try {
			PipeStandardOut.startPipe();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {			
			String out = PipeStandardOut.readLine();
			if(out != "") 
			{
				addLine(out);
				setText();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addLine(String text) {
		lines.add(text);
		if(lines.size() > linesDisplayed) {
			lines.removeFirst();
		}
	}
	
	private void setText() {
		StringBuffer buffer = new StringBuffer();

		for(String line : lines) {
			buffer.append(line);
		}
		
		super.setText(buffer.toString());
	}
}
