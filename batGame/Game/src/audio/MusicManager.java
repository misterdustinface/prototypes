package audio;

import java.util.HashMap;
import java.util.LinkedList;

import audio.nextSongSelector.NextSongSelector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class MusicManager {
	private static HashMap<String, LinkedList<String>> musicMap = new HashMap<String, LinkedList<String>>();
	private static Music currentSong;
	private static float volume = 0.5f;
	private static NextSongSelector nextSongSelector;
	private static String currentType;
	
	public static void setNextSongSelector(NextSongSelector nextSongSelector) {
		MusicManager.nextSongSelector = nextSongSelector;
	}
	
	public static void addMusic(String type, String fileName) {
		if (!musicMap.containsKey(type)) {
			musicMap.put(type, new LinkedList<String>());
		}
		
		musicMap.get(type).add(fileName);
	}
	
	public static void playFile(String file) {		
		loadAndPlayFile(file);
	}
	
	public static void playRandom(String type) {
		LinkedList<String> files = musicMap.get(type);
		int fileNumber = (int)(Math.random() * (files.size() - 1));
		String file = files.get(fileNumber);
		currentType = type;
		
		loadAndPlayFile(file);
	}
	
	public static void loopCurrent() {
		currentSong.setLooping(true);
	}
	
	public static void pauseCurrent() {
		currentSong.pause();
	}
	
	public static void resumeCurrent() {
		playCurrent();
	}
	
	public static void nextSong() {
		if(nextSongSelector != null) {
			disposeCurrentIfNotNull();
			currentSong = nextSongSelector.getNextSong(musicMap, currentSong, currentType);
			playCurrent();
		}
	}
	
	public static void stopCurrent() {
		currentSong.stop();
		currentSong.dispose();
		currentSong = null;
		currentType = null;
	}
	
	/**
	 * 
	 * @param newVolume values 0f - 1f 0 is no sound 1 is loudest
	 */
	public static void setVolume(float newVolume) {
		volume = newVolume;
		if(currentSong != null) {
			currentSong.setVolume(newVolume);
		}
	}
	
	/**
	 * 
	 * @return volume values 0f - 1f 0 is no sound 1 is loudest
	 */
	public static float getVolume() {
		return volume;
	}
	
	private static void disposeCurrentIfNotNull() {
		if(currentSong != null) {
			currentSong.dispose();
			currentSong = null;
		}
	}
	private static OnCompletionListener onCompletionListener = new OnCompletionListener() {
		
		@Override
		public void onCompletion(Music music) {
			Gdx.app.log("Music", "music completed ");
			
			//RandomSameType
			//NextTypeRandom
			//NewRandomRandom
			//NextSong
			if(nextSongSelector != null) {
				currentSong = nextSongSelector.getNextSong(musicMap, music, currentType);
				playCurrent();
			} else {
				music.dispose();
			}
		}
	};

	private static void loadAndPlayFile(String file) {
		disposeCurrentIfNotNull();
		
		currentSong = Gdx.audio.newMusic(Gdx.files.internal(file));
		playCurrent();
	}
	
	private static void playCurrent() {
		Gdx.app.log("Music", "playing music");
		currentSong.setVolume(volume);
		currentSong.setOnCompletionListener(onCompletionListener);
		currentSong.getPosition();
		currentSong.play();
	}
}
