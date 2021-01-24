package Menus;

import Miscs.Levels;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Miscs.Sounds.*;

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
    private void background() {
        back = new JLabel();
        back.setIcon(new ImageIcon("gfx/first.pvz"));
        back.setBounds(0, 0, 860, 460);
        this.add(back);
        newButton = new JLabel();
        newButton.setIcon(new ImageIcon("gfx/button.pvz"));
        SpringLayout layout = new SpringLayout();
        JLabel n = new JLabel();
        newButton.setLayout(layout);
        n.setText("New Game");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, n, 0, SpringLayout.HORIZONTAL_CENTER, newButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, n, 0, SpringLayout.VERTICAL_CENTER, newButton);

        back.add(newButton);
        newButton.add(n);
        newButton.setBounds(360, 340, 100, 45);
        newButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                newButton.setIcon(new ImageIcon("gfx/buttonHover.pvz"));
                mute();
                new Thread(() -> new Game(new Levels(), muted)).start();
                dispose();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                newButton.setIcon(new ImageIcon("gfx/button.pvz"));
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
}
