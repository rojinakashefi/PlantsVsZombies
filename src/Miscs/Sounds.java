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
    }

    public static void backPlay(int number) {
    }

    public static void play(int number) {
    }

    public static void play(String typeOfShield) {

    }

    private static void startSound(FileInputStream file) throws JavaLayerException, IOException {
        }
    }
}
