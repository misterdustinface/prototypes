package devan.sound;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.utils.Disposable;

import devan.utilities.Shuffler;

public class MusicPlaylist implements Disposable{

	private ArrayList<String> 	files; 
	private int 				trackIndex;
	private Music  				currentMusic;
	private String 				currentFile;
	
	public MusicPlaylist(){
		files 		= new ArrayList<String>();
		trackIndex 	= 0;
	}
	
	public MusicPlaylist(String[] tracks){
		files 		= new ArrayList<String>();
		trackIndex 	= 0;
		addTracks(tracks);
	}
	
	public void addTracks(String[] tracks){ 
		for(String track : tracks){
			addTrack(track);
		}
	}
	
	public void addTrack(String file){ 
		if(! files.contains(file)){
			files.add(file); 
		}
	}
	public int getNumberOfTracks(){ return files.size(); }
	public boolean isEmpty(){ return getNumberOfTracks() <= 0; }
	
	public void removeTrack(String file) throws PlaylistEmptyException{
		if(isEmpty()){throw new PlaylistEmptyException("Cannot remove track from an empty playlist.");}
		files.remove(file);
		if(currentFile == file){ nextTrack(); }
	}
	
	public void playTrack(String file){
		addTrack(file);
		if(! isFileSameAsCurrent(file)){
			currentMusic = loadTrack(file);
			currentMusic.setLooping(false);
			try { 
				play();
			} catch (PlaylistEmptyException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void play() throws PlaylistEmptyException{  
		if(isEmpty()){throw new PlaylistEmptyException("Cannot play track when playlist is Empty.");}
		if(currentMusic == null){ playTrack(files.get(trackIndex)); }
		if(! currentMusic.isPlaying()){
			currentMusic.play(); 
			currentMusic.setOnCompletionListener(onMusicCompletionListener);
		}
	}
	public void loopTrack() throws PlaylistEmptyException{  
		if(isEmpty()){throw new PlaylistEmptyException("Cannot loop when there is no track.");}
		if(currentMusic != null){ currentMusic.setLooping(true); }
	}
	public void pause() throws PlaylistEmptyException{ 
		if(isEmpty()){throw new PlaylistEmptyException("Cannot pause when there is no track.");}
		if(currentMusic != null){ currentMusic.pause(); }
	}
	public void stop() throws PlaylistEmptyException{  
		if(isEmpty()){throw new PlaylistEmptyException("Cannot stop when there is no track.");}
		if(currentMusic != null){ endCurrentMusic(); }
	}
	
	public void nextTrack() throws PlaylistEmptyException{
		if(isEmpty()){throw new PlaylistEmptyException("Cannot go to next track when playlist is empty.");}
		stop();
		if(++trackIndex >= files.size()){ trackIndex = 0; }
		if(isFileSameAsCurrent(files.get(trackIndex))){
			if(++trackIndex >= files.size()){ trackIndex = 0; }
		}
		playTrack(files.get(trackIndex));
	}
	
	@SuppressWarnings("unchecked")
	public void shuffleTracks(){
		try {
			files = Shuffler.shuffleArrayList(files);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private Music loadTrack(String file){ 
		currentFile = file;
		return Gdx.audio.newMusic(Gdx.files.internal(file)); 
	}
	
	private boolean isFileSameAsCurrent(String file){ return currentFile == file; }
	
	public void manipulateCurrentTrack(MusicManipulator manipulator){
		manipulator.manipulate(currentMusic);
	}
	
	@Override
	public void dispose() {
		endCurrentMusic();
	}
	private void endCurrentMusic(){
		currentMusic.stop();
		currentMusic.dispose();
	}
	
	private OnCompletionListener onMusicCompletionListener = new OnCompletionListener(){
		@Override
		public void onCompletion(Music music) {
			if(! music.isLooping()){
				try {
					nextTrack();
				} catch (PlaylistEmptyException e) {
					e.printStackTrace();
				}
			}
		}
	};
}
