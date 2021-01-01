package Menus;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;

import static Miscs.Sounds.IN_GAME;

/**
 * This class shows the main menu of the game
 *
 * DONE: Background Sound Effect
 *       Main Frame
 *
 * TODO: Many... :)
 *
 */

public class Game extends JFrame {
    public Game() {
        setSize(800, 600);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        new Sounds(IN_GAME);
    }

    public static void main(String[] args) {
        new Game();
    }
}
