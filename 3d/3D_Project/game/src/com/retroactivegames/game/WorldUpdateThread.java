package com.retroactivegames.game;

import devan.core.world.DynamicWorld;

/**
 * ):
 * @author dustin
 */
public class WorldUpdateThread implements Runnable{

	public final int UPDATES_PER_TENTH_SECOND;
	public DynamicWorld world;
	
	private boolean shouldUpdateWorld;
	private boolean shouldRun;
	
	private int updateCounter;
	private long lastUpdateCounterStartPeriod;
	
	public WorldUpdateThread(final int UPDATES_PER_SECOND, DynamicWorld WORLD){
		world 				= WORLD;
		shouldUpdateWorld 	= true;
		shouldRun 			= true;
		UPDATES_PER_TENTH_SECOND = UPDATES_PER_SECOND/10;
	}
	
	
	@Override
	public void run() {
		
		while(shouldRun){
			if(shouldUpdateWorld && updateCounter < UPDATES_PER_TENTH_SECOND){
				world.update();
				if(updateCounter == 0){
					lastUpdateCounterStartPeriod = System.currentTimeMillis();
				}
				++updateCounter;
			}else if(updateCounter >= UPDATES_PER_TENTH_SECOND){
				if(System.currentTimeMillis() >= lastUpdateCounterStartPeriod + 100){
					try {
						Thread.sleep(System.currentTimeMillis() - lastUpdateCounterStartPeriod);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					updateCounter = 0;
				}
			}
		}
		
	}
	
	public void pause(){
		shouldUpdateWorld = false;
	}
	public void resume(){
		shouldUpdateWorld = true;
	}
	public void exit(){
		shouldUpdateWorld 	= false;
		shouldRun 			= false;
	}

}
