package Engine;
import javafx.scene.media.*;

import java.io.File;

public class SoundEngine {
    MediaPlayer beginningSound;
    MediaPlayer chompSound;
    MediaPlayer deathSound;
    MediaPlayer eatFruitSound;
    MediaPlayer eatGhostSound;

    public void start() {

        beginningSound = new MediaPlayer(new Media(new File("Pacmanv2/src/PacmanSound/pacman_beginning.wav").toURI().toString()));
        chompSound = new MediaPlayer(new Media(new File("Pacmanv2/src/PacmanSound/pacman_chomp.wav").toURI().toString()));
        deathSound = new MediaPlayer(new Media(new File("Pacmanv2/src/PacmanSound/pacman_death.wav").toURI().toString()));
        eatFruitSound = new MediaPlayer(new Media(new File("Pacmanv2/src/PacmanSound/pacman_eatfruit.wav").toURI().toString()));
        eatGhostSound = new MediaPlayer(new Media(new File("Pacmanv2/src/PacmanSound/pacman_eatghost.wav").toURI().toString()));

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
