import java.applet.*;

public class Sound {
	
	public static final Sound backgroundMusic = new Sound ("/BackgroundMusic.wav");
	public static final Sound coinNoise = new Sound ("/CoinNoise.wav");
	public static final Sound MenuMusic = new Sound ("/MenuMusic.wav");
	public static final Sound Movement = new Sound ("/MovementSoundEffect.wav");
	
	
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public void play(){
		try{
			new Thread(){
				public void run(){
					clip.play();
				}
			}.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
