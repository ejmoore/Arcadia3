import java.applet.*;

public class Sound {
	
	public static final Sound backgroundMusic = new Sound ("sounds/BackgroundMusic.wav");
	public static final Sound coinNoise = new Sound ("sounds/CoinNoise.wav");
	public static final Sound MenuMusic = new Sound ("sounds/MenuMusic.wav");
	public static final Sound Movement = new Sound ("sounds/MovementSoundEffect.wav");
	public static final Sound Death = new Sound ("sounds/Death Explosion.wav");
	
	public AudioClip clip;
	
	Thread sound1;
		
	
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public void play(){
		try{
			sound1 = new Thread();
			clip.play();
			sound1.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public void stop(){
		clip.stop();
	}
	
}
