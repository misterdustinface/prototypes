package audio.nextSongSelector;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.audio.Music;

public abstract class NextSongSelector {
	public abstract Music getNextSong(HashMap<String, LinkedList<String>> musicMap, Music oldSong, String oldType);
}
