package com.retroactivegames.game;

public class BlockGrid {

	public final static int EMPTY_BLOCK = 0;
	
	protected int[][] blocks;
	
	public BlockGrid(int WIDTH, int HEIGHT){
		blocks = new int[HEIGHT][WIDTH];
	}
	public BlockGrid(int[][] BLOCKS){
		blocks = BLOCKS;
	}
	
	final public int getRows(){ return blocks.length; }
	final public int getCols(){ return getLongestColumn(); }
	private int getLongestColumn(){
		int max = 0;
		for(int i = 0; i < getRows(); ++i){
			if(blocks[i].length > max){
				max = blocks[i].length;
			}
		}
		return max;
	}
	
	public int getBlock(int row, int col){ 
		try{
			return blocks[row][col]; 
		}catch(Exception e){
			return EMPTY_BLOCK;
		}
	}
	
	final public void setBlock(int row, int col, int blockType){ blocks[row][col] = blockType; }
	
	final public boolean isBlockEmpty(int row, int col){ return blocks[row][col] == EMPTY_BLOCK; }
	final public boolean isBlockFilled(int row, int col){ return blocks[row][col] != EMPTY_BLOCK; }

	final public void clear(){
		for(int row = 0; row < getRows(); ++row){
			for(int col = 0; col < getCols(); ++col){
				blocks[row][col] = EMPTY_BLOCK;
			}
		}
	}
	
	final public int[][] copy(){ return blocks.clone(); }
}
