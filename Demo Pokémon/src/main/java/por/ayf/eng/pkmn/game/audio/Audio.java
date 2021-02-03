package por.ayf.eng.pkmn.game.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import por.ayf.eng.pkmn.util.Util;

/**
 *  Class that contain methods for the audio of the game.
 * 
 *  @author: Ángel Yagüe Flor.
 *  @version: 1.0 - Stable.
 */

public class Audio {
	
	/**
	 *  Method that play a sound in the game.
	 * 
	 *  @param audio: Clip that storage the sound.
	 *  @param name: Name the file.
	 */
	
	public static void playSound(Clip audio, String name) {
		try {
			audio = AudioSystem.getClip();
			audio.open(AudioSystem.getAudioInputStream(Audio.class.getResource("/sounds/" + name)));
		} catch (Exception ex) {
			Util.logMessage(Util.LEVEL_ERROR, "Ha ocurrido un error al recuperar un audio.", Audio.class, ex);
		}
		audio.start();
	}
	
	/**
	 *  Method that play the music of the game in loop.
	 * 
	 *  @param audio: Clip that storage the music.
	 *  @param name: Name of the file.
	 *  @param start: Frame from where starts the loop.
	 *  @param end: Frame from where ends the loop.
	 */
	
	public static void playBackgroundMusic(Clip audio, String name, int start, int end) {
		try {
			audio = AudioSystem.getClip();
			audio.open(AudioSystem.getAudioInputStream(Audio.class.getResource("/music/" + name)));
		} catch (Exception ex) {
			Util.logMessage(Util.LEVEL_ERROR, "Ha ocurrido un error al recuperar un audio.", Audio.class, ex);
		}
		audio.setLoopPoints(start, end);
		audio.start();
		audio.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
}
