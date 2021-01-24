package Menus;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;

import static Miscs.Sounds.MAIN_MENU;

/**
 * This class shows the main menu of the game
 *
 * DONE: Background Sound Effect
 *       Main Frame
 *
 * TODO: Many... :)
 *
 */

public class MainMenu extends JFrame {
    JLabel back, newButton, loadButton, soundButton, exitButton;
    public MainMenu() {
        setVisible(true);

       // background();

        //Game Page specs
        setSize(860, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        Sounds.backPlay(MAIN_MENU);
    }
}
