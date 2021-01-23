package Miscs;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Sounds {
    public static ArrayList<Player> backgroundMusic = new ArrayList<>();
    public static boolean muted = false;
    public final static int MAIN_MENU = 0, CHOOSE_DECK = 1, IN_GAME = 2, SHOOT_PEA = 3, MOWER = 4, EAT_PLANTS = 5,
            GRUNT = 6, INSUFFICIENT = 7, WIN = 8, LOSE = 9, STARTING = 10, READY = 11, ZOMBIES_COMING = 12, CHERRY_EXPLOSION = 13,
            SELECT = 14, PLANT = 15, FREEZE = 16;

    public final static String NONE = "none", PLASTIC = "plastic", METAL = "metal";
    public static void mute() {
        for (Player player : backgroundMusic) {
            player.close();
        }
    }

    public static void backPlay(int number) {

    }

    public static void play(int number) {
        if (!muted)
            new Thread(() -> {
                FileInputStream file;
                try {
                    switch (number) {
                        case SHOOT_PEA -> {
                            if (new Random().nextInt(2) == 0) file = new FileInputStream("sfx/game/shoot1.pvz");
                            else file = new FileInputStream("sfx/game/shoot2.pvz");
                        }

                        case PLANT -> file = new FileInputStream("sfx/game/plant.pvz");
                        case FREEZE -> file = new FileInputStream("sfx/game/frozen.pvz");
                        case GRUNT -> file = new FileInputStream("sfx/game/grunt1.pvz");
                        case CHERRY_EXPLOSION -> file = new FileInputStream("sfx/game/exCherry.pvz");

                        default -> throw new RuntimeException("Error in Sounds.play(1) switch");
                    }
                    startSound(file);
                } catch (JavaLayerException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
    }

    public static void play(String typeOfShield) {

    }

    private static void startSound(FileInputStream file) throws JavaLayerException, IOException {
        if (file != null) {
            Player player = new Player(file);
            player.play();
            player.close();
            file.close();

        }
        }
    }

