package graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

// gdx Bitmaps seem pretty unusable. 
// There must be a way to use them, but they seem pretty determined to hide that functionality.
// The lack of comments does not help.
public class Bitmap {
	
	private Color[][] colorBits;
	final private int WIDTH;
	final private int HEIGHT;
	//private int[][] intBits;
	
	//Can make a factory "BitmapBuilder" if desired.
	// This could also take a "BitmapData" object. It would most likely just be a wrapper / decoder.
	public Bitmap(int pixelsTall, int pixelsWide){
		WIDTH 	= pixelsWide;
		HEIGHT 	= pixelsTall;
		colorBits = new Color[pixelsTall][pixelsWide];
	}
	public Bitmap(Bitmap other){
		HEIGHT = other.colorBits.length;
		WIDTH  = other.colorBits[0].length;
		this.colorBits = other.colorBits.clone();
	}
	
	public void setColorAtPosition(int row, int col, Color color){
		colorBits[row][col] = color;
	}
	
	public void setColorInArea(int row, int col, int width, int height, Color color){
		//too tired must sleep
	}
	
//	void encrypt(){
//		Color.r
//		Color.rgba8888(r, g, b, a);
//	}
	
//	void decrypt(){
//		Color temp = new Color();
//		for(int[] line : intBits){
//			for(int rgbaIntBit : line){
//				Color.rgba8888ToColor(temp, rgbaIntBit);
//			}
//		}
//	}
	
	
	
	
}
