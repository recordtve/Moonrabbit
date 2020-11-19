package MoonRabbit.entity;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	// ------------------------------------ Attribute ------------------------------------
	private Clip bgClip;
	private Clip effectClip;
	
	private AudioInputStream bgAis;
	private AudioInputStream effectAis;
	
	private boolean isEffect;
	private boolean isBgm;
	
	private String bgmFilename;
	private String effectFilename;
	
	// ------------------------------------ Constructor ------------------------------------
	public Music() {
		this(null);
	}
	
	public Music(String bgmFilename) {
		this(bgmFilename, null);
	}
	
	public Music(String bgmFilename, String effectFilename) {
		isBgm = true;
		isEffect = true;

		if(effectFilename == null)
			isEffect = false;
		
		this.bgmFilename = bgmFilename;
		this.effectFilename = effectFilename;
		
		playMusic(bgmFilename);
//		playEffect(effectFilename);
	}

	// ------------------------------------ Functions ------------------------------------
	// bgSound
	void playMusic(String filename) {
		if (isBgm) {
			try {
				bgAis = AudioSystem.getAudioInputStream(new File(filename));
				bgClip = AudioSystem.getClip();

				bgClip.open(bgAis);
				bgClip.start();
				bgClip.loop(Integer.MIN_VALUE);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void musicOff() {
		bgClip.stop();
	}

	// effectSound
	public void playEffect() {
		if (isEffect) {
			try {
				effectAis = AudioSystem.getAudioInputStream(new File(effectFilename));
				effectClip = AudioSystem.getClip();
				effectClip.open(effectAis);
				effectClip.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void effectStart() {
		if(isEffect)
			effectClip.loop(1);
	}
	
	// ------------------------------------ Getters/Setters ------------------------------------
	public void setBgm(boolean bgm) {
		isBgm = bgm;
	}

	public void setEff(boolean effect) {
		isEffect = effect;
	}

}
