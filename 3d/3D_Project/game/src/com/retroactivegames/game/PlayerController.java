package com.retroactivegames.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import devan.core.actors.Player;
import devan.input.VirtualInput;
import devan.input.button.VirtualButtonPressedListener;
import devan.input.button.VirtualButtonReleasedListener;
import devan.input.gdx.InputDriver;

/**
 * @author dustin
 */
public class PlayerController {

	private InputDriver inputDriver;
	private VirtualInput vInput;
	
	private Player player;
	
	public PlayerController(Player P){
		player = P;
		setPlayerControls();
	}
	
	private void setPlayerControls(){
		inputDriver = new InputDriver();
		vInput 		= new VirtualInput();
		vInput.initDriver(inputDriver);

		vInput.mapVKey("turn left", Keys.LEFT);
		vInput.mapVKey("turn right", Keys.RIGHT);
		vInput.mapVKey("walking forward", Keys.W);
		vInput.mapVKey("walking backward", Keys.S);
		vInput.mapVKey("strafe left", Keys.A);
		vInput.mapVKey("strafe right", Keys.D);
		
		vInput.mapVKey("fly up", 	Keys.E);
		vInput.mapVKey("fly down", 	Keys.Q);
		
		vInput.addKeyPressed("turn left", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.rotating = true;
				player.rotationAmount = (float)Math.PI/180f;
			}
		});
		vInput.addKeyPressed("turn right", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.rotating = true;
				player.rotationAmount = (float)-Math.PI/180f;
			}
		});
		vInput.addKeyReleased("turn left", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.rotating = false;
			}
		});
		vInput.addKeyReleased("turn right", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.rotating = false;
			}
		});
		
		vInput.addKeyPressed("walking forward", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.walking = true;
				player.walkSpeed = 1.5f;
			}
		});
		vInput.addKeyPressed("walking backward", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.walking = true;
				player.walkSpeed = -1.5f;
			}
		});
		vInput.addKeyReleased("walking forward", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.walking = false;
			}
		});
		vInput.addKeyReleased("walking backward", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.walking = false;
			}
		});
		
		vInput.addKeyPressed("strafe left", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.strafing = true;
				player.strafeSpeed = -1.5f;
			}
		});
		vInput.addKeyReleased("strafe left", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.strafing = false;
			}
		});
		vInput.addKeyPressed("strafe right", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.strafing = true;
				player.strafeSpeed = 1.5f;
			}
		});
		vInput.addKeyReleased("strafe right", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.strafing = false;
			}
		});
		
		vInput.addKeyPressed("fly up", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.flying = true;
				player.flySpeed = 0.05f;
			}
		});
		vInput.addKeyReleased("fly up", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.flying = false;
			}
		});
		vInput.addKeyPressed("fly down", new VirtualButtonPressedListener(){
			@Override
			public void keyPressed() {
				player.flying = true;
				player.flySpeed = -0.05f;
			}
		});
		vInput.addKeyReleased("fly down", new VirtualButtonReleasedListener(){
			@Override
			public void keyReleased() {
				player.flying = false;
			}
		});
		
		Gdx.input.setInputProcessor(inputDriver.getInputProcessor());
	}
}
