package com.retroactivegames.game;

public class TetrisWorld extends BlockGrid{

	private Tetrominoe reserveTetrominoe;
	private Tetrominoe fallingTetrominoe;
	private int rowsCleared;
	
	final private long INITIAL_TIME_TO_WAIT_UNTIL_NEXT_GRAVITY_SHIFT_DOWN 			= 1000;
	final private long LEVEL_UP_WAIT_TIME_DECREMENTER 								= 100;
	final private int  NUMBER_OF_ROWS_NEEDED_TO_MATCH_UNTIL_DIFFICULTY_IS_INCREASED = 10;
	
	private int nextLevelUpGoal = NUMBER_OF_ROWS_NEEDED_TO_MATCH_UNTIL_DIFFICULTY_IS_INCREASED;
	
	private long timeToWaitUntilNextGravityShiftDown;
	private long lastTimeToWait;
	
	private int  difficultyLevel;
	
	private boolean hasLost;
	
	public TetrisWorld(int WIDTH, int HEIGHT) {
		super(WIDTH, HEIGHT);
		reset();
	}
	
	public void reset(){
		clear();

		reserveTetrominoe = null;
		TetrominoeFactory.clear();
		
		TetrominoeFactory.produceTetrominoe(getWidth(), getHeight());
		TetrominoeFactory.produceTetrominoe(getWidth(), getHeight());
		fallingTetrominoe = TetrominoeFactory.pop();

		rowsCleared 	= 0;
		lastTimeToWait 	= System.currentTimeMillis();
		
		timeToWaitUntilNextGravityShiftDown = INITIAL_TIME_TO_WAIT_UNTIL_NEXT_GRAVITY_SHIFT_DOWN;
		difficultyLevel = 1;
		hasLost = false;
	}
	
	public int getNumberOfClearedRows(){ return rowsCleared; }
	public int getDifficulty(){ return difficultyLevel; }
	
	private void checkMatches(){
		// DEPENDENCY ON ROW CHECK ORDER GOING OPPOSITE OF ALL INTERAL LOOP CHECKS.
		for(int i = getRows()-1; i >=0; --i){
			if(isRowFull(i)){
				clearRow(i);
				moveFloatingRowsDown();
			}
		}
	}
	
	private void moveFloatingRowsDown(){
		for(int q = 0; q < getRows()-1; ++q){
			if(isRowEmpty(q)){ 
				swapRows(q, findFirstNonEmptyRow(q));
			}
		}
	}
	
	private int findFirstNonEmptyRow(int fromRow){
		for(int row = fromRow; row < getRows(); ++row){
			if(!isRowEmpty(row)){ return row; }
		}
		return fromRow;
	}
	
	private void updateTimer(){
		if(System.currentTimeMillis() > lastTimeToWait + timeToWaitUntilNextGravityShiftDown){
			shiftTetrominoeDown();
			lastTimeToWait = System.currentTimeMillis();
		}
	}
	
	private void updateLevelUpData(){
		if(rowsCleared >= nextLevelUpGoal){
			++difficultyLevel;
			nextLevelUpGoal = rowsCleared + NUMBER_OF_ROWS_NEEDED_TO_MATCH_UNTIL_DIFFICULTY_IS_INCREASED;
			timeToWaitUntilNextGravityShiftDown = INITIAL_TIME_TO_WAIT_UNTIL_NEXT_GRAVITY_SHIFT_DOWN - (difficultyLevel-1)*LEVEL_UP_WAIT_TIME_DECREMENTER;
		}
	}
	
	public void update(){
		if(!hasLost){ updateTimer(); }
	}
	
	public boolean hasLost(){ return hasLost; }
	
	public void newFallingTetrominoe(){
		TetrominoeFactory.produceTetrominoe(getWidth(), getHeight());
		setCurrentFallingTetrominoe(TetrominoeFactory.pop());
	}
	
	private void setCurrentFallingTetrominoe(Tetrominoe current){		
		if(fallingTetrominoe.getRow() + fallingTetrominoe.getHeight() <= getHeight()){
			writeCurrentTetrominoeToWorld();// TODO - move to a landing collision thing (for feature purposes)
			fallingTetrominoe = current;
		}else{
			hasLost 		= true;
		}
		
		checkMatches();
		updateLevelUpData();
	}
	
	private void writeCurrentTetrominoeToWorld(){
		for(int row = fallingTetrominoe.getRow(); row < fallingTetrominoe.getRow() + fallingTetrominoe.getHeight(); ++row){
			for(int col = fallingTetrominoe.getCol(); col < fallingTetrominoe.getCol() + fallingTetrominoe.getWidth(); ++col){
				if(fallingTetrominoe.getBlockRelativeToCoordinate(row, col) != EMPTY_BLOCK){
					blocks[row][col] = fallingTetrominoe.getBlockRelativeToCoordinate(row, col);
				}
			}
		}
	}
	
	@Override
	public int getBlock(int row, int col){
		int block = super.getBlock(row, col);
		if(block == EMPTY_BLOCK){
			return fallingTetrominoe.getBlockRelativeToCoordinate(row, col);
		}else{
			return block;
		}
	}
	
	private boolean isFallingTetrominoeCollidingWithWorldBlock(){
		for(int row = fallingTetrominoe.getRow(); row < fallingTetrominoe.getRow() + fallingTetrominoe.getHeight(); ++row){
			for(int col = fallingTetrominoe.getCol(); col < fallingTetrominoe.getCol() + fallingTetrominoe.getWidth(); ++col){
				if(fallingTetrominoe.getBlockRelativeToCoordinate(row, col) != EMPTY_BLOCK
				&& super.getBlock(row, col) != EMPTY_BLOCK){
					return true;
				}
			}
		}
		return false;
	}
	private boolean isFallingTetrominoeOutOfBounds(){
		return (fallingTetrominoe.getCol() < 0 || fallingTetrominoe.getRow() < 0 
			|| 	fallingTetrominoe.getCol() + fallingTetrominoe.getWidth() > getWidth()
			|| 	fallingTetrominoe.getRow() + fallingTetrominoe.getHeight() > getHeight());
	}
	private void keepFallingTetrominoeInBounds(){
		if(fallingTetrominoe.getCol() < 0){
			fallingTetrominoe.setCol(0);
		}
		else if(fallingTetrominoe.getCol() + fallingTetrominoe.getWidth() > getWidth()){
			fallingTetrominoe.setCol(getWidth() - fallingTetrominoe.getWidth());
		}
		
		if(fallingTetrominoe.getRow() < 0){
			fallingTetrominoe.setRow(0);
			newFallingTetrominoe();
		}
	}
	
	public int getWidth(){ return getCols(); }
	public int getHeight(){ return getRows(); }

	public void clearRow(int row){
		blocks[row] = new int[getCols()];
		++rowsCleared;
	}
	public void swapRows(int A, int B){
		int[] temp 	= blocks[A].clone();
		blocks[A] 	= blocks[B];
		blocks[B] 	= temp;
	}
	
	public boolean isRowFull(int row){
		for(int col = 0; col < getCols(); ++col){
			if(blocks[row][col] == EMPTY_BLOCK){
				return false;
			}
		}
		return true;
	}
	public boolean isRowEmpty(int row){
		for(int col = 0; col < getCols(); ++col){
			if(blocks[row][col] != EMPTY_BLOCK){
				return false;
			}
		}
		return true;
	}
	
	public void shiftTetrominoeLeft(){
		fallingTetrominoe.shiftLeft();
		keepFallingTetrominoeInBounds();
		
		// handle if collision - reverse movement
		if(isFallingTetrominoeCollidingWithWorldBlock()){
			fallingTetrominoe.shiftRight();
			keepFallingTetrominoeInBounds();
		}
	}
	public void shiftTetrominoeRight(){
		fallingTetrominoe.shiftRight();
		keepFallingTetrominoeInBounds();
		
		// handle if collision - reverse movement
		if(isFallingTetrominoeCollidingWithWorldBlock()){
			fallingTetrominoe.shiftLeft();
			keepFallingTetrominoeInBounds();
		}
	}
	public void shiftTetrominoeDown(){
		fallingTetrominoe.shiftDown();
		keepFallingTetrominoeInBounds();

		// handle if collision - reverse movement
		if(isFallingTetrominoeCollidingWithWorldBlock()){
			fallingTetrominoe.shiftUp();
			keepFallingTetrominoeInBounds();
			
			newFallingTetrominoe();
		}
	}
	public void shiftTetrominoeUp(){
		fallingTetrominoe.shiftUp();
		keepFallingTetrominoeInBounds();

		// handle if collision - reverse movement
		if(isFallingTetrominoeCollidingWithWorldBlock()){
			fallingTetrominoe.shiftDown();
			keepFallingTetrominoeInBounds();
		}
	}
	public void dropTetrominoeDown(){
		// handle if collision - reverse movement
		while(! isFallingTetrominoeCollidingWithWorldBlock()
		   && fallingTetrominoe.getRow() >= 0){
			fallingTetrominoe.shiftDown();
		}
		fallingTetrominoe.shiftUp();
		newFallingTetrominoe();
	}
	
	public void rotateTetrominoeClockwise(){
		if(!isFallingTetrominoeOutOfBounds()){
			fallingTetrominoe.rotateClockwise();
			// handle if collision - reverse movement
			if(isFallingTetrominoeCollidingWithWorldBlock()){
				fallingTetrominoe.rotateCounterclockwise();
			}else if(isFallingTetrominoeOutOfBounds()){
				keepFallingTetrominoeInBounds();
			}
		}
	}
	public void rotateTetrominoeCounterclockwise(){
		if(!isFallingTetrominoeOutOfBounds()){
			fallingTetrominoe.rotateCounterclockwise();
			// handle if collision - reverse rotation
			if(isFallingTetrominoeCollidingWithWorldBlock()){
				fallingTetrominoe.rotateClockwise();
			}else if(isFallingTetrominoeOutOfBounds()){
				keepFallingTetrominoeInBounds();
			}
		}
	}
	
	public void swapTetrominoe(){
		TetrominoeFactory.resetTetrominoePosition(fallingTetrominoe, getWidth(), getHeight());
		if(!hasReservedTetrominoe()){
			TetrominoeFactory.produceTetrominoe(getWidth(), getHeight());
			reserveTetrominoe = fallingTetrominoe;
			fallingTetrominoe = TetrominoeFactory.pop();
		}else{
			TetrominoeFactory.resetTetrominoePosition(reserveTetrominoe, getWidth(), getHeight());
			Tetrominoe temp = reserveTetrominoe;
			reserveTetrominoe = fallingTetrominoe;
			fallingTetrominoe = temp;
		}
	}
	
	public Tetrominoe getReservedTetrominoe(){
		return reserveTetrominoe;
	}
	public boolean hasReservedTetrominoe(){
		return reserveTetrominoe != null;
	}
}
