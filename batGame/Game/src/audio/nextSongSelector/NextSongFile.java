package audio.nextSongSelector;

import java.util.HashMap;
import java.util.LinkedList;

import audio.MusicManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class NextSongFile extends NextSongSelector {

	String file;
	
	public NextSongFile(String file) {
		this.file = file;
	}
	
	@Override
	public Music getNextSong(HashMap<String, LinkedList<String>> musicMap,
			Music oldSong, String oldType) {
		MusicManager.setNextSongSelector(null);
		return Gdx.audio.newMusic(Gdx.files.internal(file));
	}

}
