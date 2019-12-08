package Engine;
import javafx.scene.media.*;


public class SoundEngine {
    AudioClip beginningSound;
    AudioClip chompSound;
    AudioClip deathSound;
    AudioClip eatFruitSound;
    AudioClip eatGhostSound;

    public void start() {

        beginningSound = new AudioClip(SoundEngine.class.getClassLoader().getResource("PacmanSound/pacman_beginning.wav").toString());
        chompSound = new AudioClip(SoundEngine.class.getClassLoader().getResource("PacmanSound/pacman_chomp.wav").toString());
        deathSound = new AudioClip(SoundEngine.class.getClassLoader().getResource("PacmanSound/pacman_death.wav").toString());
        eatFruitSound = new AudioClip(SoundEngine.class.getClassLoader().getResource("PacmanSound/pacman_eatfruit.wav").toString());
        eatGhostSound = new AudioClip(SoundEngine.class.getClassLoader().getResource("PacmanSound/pacman_eatghost.wav").toString());

    }

    public void playBeginningSound(){
        beginningSound.play();
    }

    public void playChompSound(){
        chompSound.play();
    }

    public void playDeathSound(){
        deathSound.play();
    }

    public void playEatFruitSound(){
        eatFruitSound.play();
    }

    public void playEatGhostSound(){
        eatGhostSound.play();
    }
}
