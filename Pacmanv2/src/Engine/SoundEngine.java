package Engine;
import javafx.scene.media.*;


public class SoundEngine {
    AudioClip beginningSound;
    AudioClip chompSound;
    AudioClip deathSound;
    AudioClip eatFruitSound;
    AudioClip eatGhostSound;

    public void start() {

        beginningSound = new AudioClip("file:Pacmanv2/PacmanSound/pacman_beginning.wav");
        chompSound = new AudioClip("file:Pacmanv2/PacmanSound/pacman_chomp.wav");
        deathSound = new AudioClip("file:Pacmanv2/PacmanSound/pacman_death.wav");
        eatFruitSound = new AudioClip("file:Pacmanv2/PacmanSound/pacman_eatfruit.wav");
        eatGhostSound = new AudioClip("file:Pacmanv2/PacmanSound/pacman_eatghost.wav");

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
