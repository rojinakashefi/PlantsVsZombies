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
        if (!muted)
            new Thread(() -> {
                FileInputStream file;
                boolean loop = false;
                try {
                    switch (number) {
                        case MAIN_MENU -> {
                            file = new FileInputStream("sfx/back/main.pvz");
                            loop = true;
                        }
                        case CHOOSE_DECK -> file = new FileInputStream("sfx/back/deck.pvz");
                        case IN_GAME -> {
                            file = new FileInputStream("sfx/back/game.pvz");
                            loop = true;
                        }
                        case ZOMBIES_COMING -> file = new FileInputStream("sfx/back/startRound.pvz");
                        case STARTING -> {
                            file = new FileInputStream("sfx/back/deck.pvz");
                            Player player = new Player(file);
                            backgroundMusic.add(player);
                            player.play(200);
                            backgroundMusic.remove(player);
                            file = null;
                        }
                        case MOWER -> file = new FileInputStream("sfx/game/mower.pvz");
                        default -> throw new RuntimeException("BackPlay switch");
                    }
                    if (file != null) {
                        Player player = new Player(file);
                        backgroundMusic.add(player);
                        player.play();
                        if (loop) {
                            while (true) player.play();
                        } else {
                            player.close();
                            file.close();
                            backgroundMusic.remove(player);
                        }
                    }
                } catch (IOException | JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
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
        if (!muted)
            new Thread(() -> {
                FileInputStream file;
                try {
                    switch (typeOfShield) {
                        case NONE -> {
                            if (new Random().nextInt(2) == 0) file = new FileInputStream("sfx/game/splat1.pvz");
                            else file = new FileInputStream("sfx/game/splat2.pvz");
                        }
                        case PLASTIC -> file = new FileInputStream("sfx/game/plastic.pvz");
                        case METAL -> {
                            if (new Random().nextInt(2) == 0) file = new FileInputStream("sfx/game/metal1.pvz");
                            else file = new FileInputStream("sfx/game/metal2.pvz");
                        }
                        default -> throw new RuntimeException("Error in Sounds.play(2) switch");
                    }
                    startSound(file);
                } catch (JavaLayerException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
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
