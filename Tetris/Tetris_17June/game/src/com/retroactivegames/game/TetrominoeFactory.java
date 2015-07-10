package com.retroactivegames.game;

import java.util.ArrayList;
import java.util.Random;

abstract public class TetrominoeFactory { 
	
	private static ArrayList<Tetrominoe> tetrominoeQueue = new ArrayList<Tetrominoe>();
	
	private static Random rand = new Random();
	private static BlockGrid[][] PRESETS = new BlockGrid[][]{
		
		//3
		new BlockGrid[]{new BlockGrid(new int[][]{	{3,3},
													{3,3}  	})
		},
		
		//4
		new BlockGrid[]{new BlockGrid(new int[][]{	{4,4,4},
													{4} 	}),
						new BlockGrid(new int[][]{	{4,4},
													{0,4},
													{0,4}  	}),
						new BlockGrid(new int[][]{	{0,0,4},
													{4,4,4} }),
						new BlockGrid(new int[][]{	{4},
													{4},
													{4,4}  	})
		},
		
		//8
		new BlockGrid[]{new BlockGrid(new int[][]{	{8,0,0},
													{8,8,8} }),
						new BlockGrid(new int[][]{	{8,8},
													{8},
													{8}  	}),
						new BlockGrid(new int[][]{	{8,8,8},
													{0,0,8} }),
						new BlockGrid(new int[][]{	{0,8},
													{0,8},
													{8,8}  	})
		},
		
		//5
		new BlockGrid[]{new BlockGrid(new int[][]{	{5},
													{5},
													{5},
													{5} 	  }),
						new BlockGrid(new int[][]{	{5,5,5,5} })
		},
		
		//2
		new BlockGrid[]{new BlockGrid(new int[][]{	{0,2},
													{2,2,2},
													{0,0,0}	}),
						new BlockGrid(new int[][]{	{2},
													{2,2},
													{2}		}),
						new BlockGrid(new int[][]{	{2,2,2},
													{0,2},
													{0}		}),
						new BlockGrid(new int[][]{	{0,2},
													{2,2},
													{0,2}	})
		},
		
		//6
		new BlockGrid[]{new BlockGrid(new int[][]{	{0,6},
													{6,6},
													{6}	}),
						new BlockGrid(new int[][]{	{6,6},
													{0,6,6} }),
		},	
		
		//1
		new BlockGrid[]{new BlockGrid(new int[][]{	{1},
													{1,1},
													{0,1}	}),
						new BlockGrid(new int[][]{	{0,1,1},
													{1,1} }),
		},
//		new BlockGrid[]{new BlockGrid(new int[][]{	{6,6,6,6},
//													{6,6,6,6},
//													{6,6,6,6},
//													{6,6,6,6}})
//		}
	};
	private static BlockGrid[] getRandomPreset(){
		return PRESETS[rand.nextInt(PRESETS.length)];
	}
	
	public static void produceTetrominoe(int worldWidth, int worldHeight){
		tetrominoeQueue.add(new Tetrominoe(getRandomPreset(), new BoardCoordinate(worldHeight, (worldWidth>>1) - 1)));
	}

	public static void push(Tetrominoe tetrino){
		tetrominoeQueue.add(0, tetrino);
	}
	public static Tetrominoe pop(){ 
		return tetrominoeQueue.remove(0);
	}
	public static Tetrominoe peek(){
		return tetrominoeQueue.get(0);
	}
	
	public static void clear(){ tetrominoeQueue.clear(); }
	
	public static void resetTetrominoePosition(Tetrominoe t, int worldWidth, int worldHeight){
		t.setRow(worldHeight);
		t.setCol((worldWidth>>1) - 1);
	}
	
}
