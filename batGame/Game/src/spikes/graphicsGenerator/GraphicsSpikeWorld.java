package spikes.graphicsGenerator;

import java.util.ArrayList;
import java.util.ListIterator;

import levelGeneration.BoundGenerator;
import world.World;
import collisionDetection.Updater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Line{
	Vector2 A;
	Vector2 B;
	Line(){
		A = new Vector2();
		B = new Vector2();
	}
	Line(Line other){
		A = other.A.cpy();
		B = other.B.cpy();
	}
	
	String distance(){
		return (int) A.dst(B) == 0 ? " " : String.valueOf((int) A.dst(B));
	}
	
	Vector2 midPoint(){
		return new Vector2((A.x + B.x)/2.0f, (A.y + B.y)/2.0f);
	}
}

public class GraphicsSpikeWorld extends World{

	ArrayList<Rectangle> boxes;
	ArrayList<Line>		 lines;
	Cursor cursor;
	
	public GraphicsSpikeWorld() {
//		super();
		init();
	}
	
	@Override
	public void init() {
		super.init();
		
		Globals.init();
		boxes = new ArrayList<Rectangle>();
		lines = new ArrayList<Line>();
		
		cursor = new Cursor(Globals.CURSOR_SPAWN_X, Globals.CURSOR_SPAWN_Y, Globals.CURSOR_Move_Speed);

		resetTitle();
	}
	
	@Override
	public void update() {
		cursor.update();
		generatorAdd();
		generatorRemove();
		
		Updater.updateAll(bat, objects, caveUpper, caveLower, collisionHandeler);
		
		Gdx.app.log("TO DUSTIN", "CHECK game-desktop/lastRun.txt FOR OUTPUT.  THIS OUTPUT LINE IS IN GraphicsSpikeWorld UPDATE METHOD...");
	}
	
	
	public Vector2 getCenter() {
		return new Vector2(cursor.x, cursor.y);
	}
	
	
	public void moveDown(){
//		   + " Slope Percent: " 	+ super.floorGenerator.getSlope());
		cursor.moveDown();
	}
	public void moveUp(){
		cursor.moveUp();
	}
	public void moveRight(){
		cursor.moveRight();
	}
	public void moveLeft(){
		cursor.moveLeft();
	}
	
	public void generatorAdd(){
		caveUpper.append(ceilingGenerator.getLine(2));
		caveLower.append(floorGenerator.getLine(2));
	}
	public void generatorRemove(){
		if(!caveUpper.isEmpty()){
			caveUpper.removeFirst();
			caveLower.removeFirst();
		}
	}
	
	public void clearAdditions(){
		boxes.clear();
		lines.clear();
	}
	
	public void click(){
		ListIterator<Rectangle> i = boxes.listIterator(); 
		while (i.hasNext()){
			Rectangle object = i.next();
			if(object.contains(cursor.x, cursor.y)) {
				// Remove Lines
				ListIterator<Line> li = lines.listIterator();
				while(li.hasNext()){
					Line line = li.next();
					if(line.A.equals(object.getCenter(new Vector2()))
					|| line.B.equals(object.getCenter(new Vector2()))){
						li.remove();
					}
				}
				
				// Remove Box
				i.remove();
				Gdx.app.log("Remove", " Block");

//				   + " Slope Percent: " 	+ super.floorGenerator.getSlope());
				return;
			}
		}
		
		addBlock(cursor.x, cursor.y);
		
	}
	
	private void addBlock(float x, float y){
		
		Rectangle currentBox = new Rectangle(x - 10, y - 10, 20, 20);
		
		// ADD LINES
		Line temp = new Line();
		temp.A = currentBox.getCenter(temp.A);
		for(Rectangle box : boxes){
			temp.B = box.getCenter(currentBox.getCenter(temp.B));
			lines.add(new Line(temp));
		}
		
		boxes.add(currentBox);
		Gdx.app.log("Added", " Block");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private TextInputListener bsl = new TextInputListener(){

		@Override
		public void input(String text) {
			text.trim();
			setBoundsSlope(Float.valueOf(text));
			Gdx.app.log("Slope Set To: ", text);
		}

		@Override
		public void canceled() {
			// TODO Auto-generated method stub
		}
	};
	private TextInputListener fpml = new TextInputListener(){

		@Override
		public void input(String text) {
			text.trim();
			setBoundsFlipPercent(Float.valueOf(text));	
			Gdx.app.log("Flip Set To: ", text);
		}

		@Override
		public void canceled() {
			// TODO Auto-generated method stub
		}
	};
	
	public void getFPS(){
		//Gdx.app.log("FPS ", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		System.out.println("FPS " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
	}
	
	public void getSlopeManipPrompt(){
		Gdx.input.getTextInput(bsl, "Change Slope", String.valueOf(BoundGenerator.ORGANIC_SLOPE));
	}
	public void getFlipPercentManipPrompt(){
		Gdx.input.getTextInput(fpml, "Change Random Flip Probability", String.valueOf(BoundGenerator.DEFAULT_RAND_FLIP_PROBABILITY));
	}
	
	private void setBoundsSlope(float amount){
		floorGenerator.setSlopeDelta(amount);
		ceilingGenerator.setSlopeDelta(amount);
		regenerate();
		resetTitle();
	}
	private void setBoundsFlipPercent(float percent){
		floorGenerator.setRandomFlipProbability(percent);
		ceilingGenerator.setRandomFlipProbability(percent);
		regenerate();
		resetTitle();
	}
	
	private void regenerate(){
		for(int i = 0; i < 500; i++){
			generatorRemove();
			generatorAdd();
		}
	}
	
	private void resetTitle(){
		System.out.println("Flip Probability: " + super.floorGenerator.getFlipProbability());
		System.out.println("Slope Percent:    " + super.floorGenerator.getSlope());
		
		Gdx.graphics.setTitle("Flip Probability: " 	+ super.floorGenerator.getFlipProbability()
						   + " Slope Percent: " 	+ super.floorGenerator.getSlope());
	}
	
	
}
