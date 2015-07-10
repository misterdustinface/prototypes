package com.retroactivegames.game;


public class Tetrominoe {
	
	private BoardCoordinate coordinate;
	private BlockGrid[] 	tetrominoeDef;
	private int 			currentGrid;
	
	public Tetrominoe(BlockGrid[] TETROMINOE_DEFINITION, BoardCoordinate COORDIANTE){
		tetrominoeDef = TETROMINOE_DEFINITION;
		coordinate = COORDIANTE;
		currentGrid = 0;
	}
	
	public int getBlockRelativeToCoordinate(int row, int col){
		row -= coordinate.row;
		col -= coordinate.col;
		return currentGrid().getBlock(row, col);
	}
	
//	public boolean intersectsWorldBlocks(BlockGrid world){
//		
//		for(int row = 0; row < currentTetrino().getRows(); ++row){
//			for(int col = 0; col < currentTetrino().getCols(); ++col){
//				if(currentTetrino().isBlockFilled(row, col) && world.isBlockFilled(row + coordinate.row, col + coordinate.col)){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	public BlockGrid currentGrid(){
		return tetrominoeDef[currentGrid];
	}
	
	public int getWidth(){ return currentGrid().getCols(); }
	public int getHeight(){ return currentGrid().getRows(); }
	
	public int getRow(){return coordinate.row;}
	public int getCol(){return coordinate.col;}
	public void setRow(int row){ coordinate.row = row; }
	public void setCol(int col){ coordinate.col = col; }
	
	public void shiftLeft(){--coordinate.col;}
	public void shiftRight(){++coordinate.col;}
	public void shiftUp(){++coordinate.row;}
	public void shiftDown(){--coordinate.row;}
	
	public void rotateClockwise(){
		++currentGrid;
		if(currentGrid > tetrominoeDef.length-1){
			currentGrid = 0;
		}
	}
	public void rotateCounterclockwise(){
		--currentGrid;
		if(currentGrid < 0){
			currentGrid = tetrominoeDef.length-1;
		}
	}
}
