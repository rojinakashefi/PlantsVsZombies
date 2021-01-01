package Miscs;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sounds {
    public final static int MAIN_MENU = 0,CHOOSE_DECK = 1, IN_GAME = 2, SHOOT_PEA = 3, LAND_PEA = 4, EAT_PLANTS = 5,
                     GRUNT = 6, WALK = 7, WIN = 8, LOSE = 9;
    public Sounds(int number) {
        FileInputStream file;
        try {
            switch (number) {
                case MAIN_MENU -> file = new FileInputStream("sfx/back/main.pvz");
                case CHOOSE_DECK -> file = new FileInputStream("sfx/back/deck.pvz");
                case IN_GAME -> file = new FileInputStream("sfx/back/game.pvz");
                case SHOOT_PEA -> file = new FileInputStream("sfx/game/shoot1.pvz");
                case LAND_PEA -> file = new FileInputStream("sfx/game/land.pvz");
                case EAT_PLANTS -> file = new FileInputStream("sfx/game/eat1.pvz");
                case GRUNT -> file = new FileInputStream("sfx/game/grunt1.pvz");
                case WALK -> file = new FileInputStream("sfx/game/walk1.pvz");
                case WIN -> file = new FileInputStream("sfx/game/win.pvz");
                case LOSE -> file = new FileInputStream("sfx/game/lose.pvz");
                default -> throw new RuntimeException("Wrong Sound Number");
            }
            Player player = new Player(file);
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
