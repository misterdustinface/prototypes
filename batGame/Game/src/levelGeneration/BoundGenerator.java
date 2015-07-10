package levelGeneration;

import java.util.Random;

import collisionDetection.Buffer;

public class BoundGenerator {
	
	final public static float ORGANIC_SLOPE = 0.0271828f; // Euler's number / Napier's constant - "e", as a percentage.
	final public static float DEFAULT_RAND_FLIP_PROBABILITY = 0.0013f; // 0.0013f
	
	final private int 	MAX_HEIGHT;
	final private int 	MIN_HEIGHT;
	final private int 	RANGE;
	//final 
	private float SLOPE_DELTA; // A magic number
	
	private Random rand = new Random();
	private float randomFlipProbability;
	
	//private Action 	currentAction;
	
	private boolean slopingDown;
	
	private int 	currentHeight;
	private int 	previousHeight;
	
	public BoundGenerator(final int MAX_HEIGHT, final int MIN_HEIGHT){
		this.MAX_HEIGHT 	= MAX_HEIGHT;
		this.MIN_HEIGHT 	= MIN_HEIGHT;
		this.RANGE			= Math.abs(MAX_HEIGHT - MIN_HEIGHT);
		this.SLOPE_DELTA 	= ORGANIC_SLOPE;
		
		randomFlipProbability = DEFAULT_RAND_FLIP_PROBABILITY;
		
		forceHeight((MAX_HEIGHT + MIN_HEIGHT) / 2);
	}
	
//	SlopeGenerator(final int MAX_HEIGHT, final int MIN_HEIGHT, float SLOPE_DELTA){
//		this.MAX_HEIGHT 	= MAX_HEIGHT;
//		this.MIN_HEIGHT 	= MIN_HEIGHT;
//		this.SLOPE_DELTA 	= SLOPE_DELTA;
//		
//		randomFlipProbability = DEFAULT_RAND_FLIP_PROBABILITY;
//		
//		forceHeight((MAX_HEIGHT + MIN_HEIGHT) / 2);
//	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// TEMP FOR TESTING
	public void setSlopeDelta(final float AMOUNT){SLOPE_DELTA = AMOUNT;}
	public void setRandomFlipProbability(final float PROBABILITY){randomFlipProbability = PROBABILITY > 1 ? 1 : PROBABILITY; }
	public String getSlope(){return String.valueOf(SLOPE_DELTA);}
	public String getFlipProbability(){return String.valueOf(randomFlipProbability);}
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void forceHeight(final int FORCED){
		setNewHeight(FORCED);
		adjustSlope();
	}
	public void forceTurn(){
		//currentAction = currentAction == Action.SLOPING_DOWN ? Action.SLOPING_UP : Action.SLOPING_DOWN;
		slopingDown = !slopingDown;
	}
	
	public int nextHeight(){
		generate();
		return currentHeight;
	}
	
	public Buffer getLine(final int WIDTH){
		Buffer line = new Buffer();
		for(int i = 0; i < WIDTH; i++)
			line.addLast(nextHeight());
		return line;
	}
	
	private void generate(){
		
		int unit = RANGE;
		
		// THIS IF/ELSE IF PART FIXES THE BUG
		if(currentHeight > MAX_HEIGHT){
			unit 	= Math.abs(MIN_HEIGHT - currentHeight); // current offset from min
		}else if(currentHeight < MIN_HEIGHT){
			unit 	= Math.abs(MAX_HEIGHT - currentHeight); // current offset from max
		}		
		
		int offset 	= (int) Math.rint(SLOPE_DELTA * rand.nextFloat() * unit);
		
		
		if(slopingDown){
			setNewHeight(currentHeight - offset);
		}else{
			setNewHeight(currentHeight + offset);
		}
		
//		switch(currentAction){
//		case SLOPING_DOWN:	setNewHeight(currentHeight - currentOffset);
//			break;
//		case SLOPING_UP:	setNewHeight(currentHeight + currentOffset);
//			break;
//			default:		setNewHeight(currentHeight);
//		}
		adjustSlope();
	}
	
	private void adjustSlope(){
		if(currentHeight >= MAX_HEIGHT){
			slopingDown = true;
			//currentAction = Action.SLOPING_DOWN;
		}else if(currentHeight <= MIN_HEIGHT){
			slopingDown = false;
			//currentAction = Action.SLOPING_UP;
		}else if(rand.nextFloat() < randomFlipProbability){
			forceTurn();
		}
	}
	
	private void setNewHeight(final int h){
		previousHeight = currentHeight;
		currentHeight  = h;
	}
	
//	enum Action{
//		SLOPING_UP, SLOPING_DOWN
//	}
}
