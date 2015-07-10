package com.retroactivegames.game;

public class BoardCoordinate {
	public int row, col;
	public BoardCoordinate(int ROW, int COL){
		row = ROW; col = COL;
	}
	public BoardCoordinate(BoardCoordinate other){
		row = other.row; col = other.col;
	}
}
