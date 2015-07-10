package devan.sound;

public class PlaylistEmptyException extends Exception{
	private static final long serialVersionUID = 1993L;
	public PlaylistEmptyException(){ super("Playlist is empty."); }
	public PlaylistEmptyException(String message){ super(message); }
}
