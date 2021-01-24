package Menus;

import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;

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
    public MainMenu() {
        setSize(800, 600);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
