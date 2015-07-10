package collisionDetection;

public class Constants {
	public static int   WIDTH           = 20;
	public static float HEIGHT          = 20.0f;
	public static float VELOCITY_FLAP   = 3.0f;
	public static float VELOCITY_GLIDE  = -0.3f;
	public static float GRAVITY_FLAP    = -0.2f;
	public static float GRAVITY_GLIDE   = -0.0f;
	public static float GRAVITY_DIVE    = -0.2f;
	
	
	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public static void setHEIGHT(float hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public static void setVELOCITY_FLAP(float vELOCITY_FLAP) {
		VELOCITY_FLAP = vELOCITY_FLAP;
	}
	public static void setVELOCITY_GLIDE(float vELOCITY_GLIDE) {
		VELOCITY_GLIDE = vELOCITY_GLIDE;
	}
	public static void setGRAVITY_FLAP(float gRAVITY_FLAP) {
		GRAVITY_FLAP = gRAVITY_FLAP;
	}
	public static void setGRAVITY_GLIDE(float gRAVITY_GLIDE) {
		GRAVITY_GLIDE = gRAVITY_GLIDE;
	}
	public static void setGRAVITY_DIVE(float gRAVITY_DIVE) {
		GRAVITY_DIVE = gRAVITY_DIVE;
	}
}
