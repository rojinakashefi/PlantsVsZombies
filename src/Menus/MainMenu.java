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

        background();

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
        loadButton = new JLabel();
        boolean loaded = Levels.load() != null;
        if (loaded)
            loadButton.setIcon(new ImageIcon("gfx/button.pvz"));
        else
            loadButton.setIcon(new ImageIcon("gfx/buttonOff.pvz"));
        loadButton.setText("Load Game");
        loadButton.setBounds(360, 390, 100, 45);
        JLabel m = new JLabel();
        loadButton.setLayout(layout);
        m.setText("Load Game");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, m, 0, SpringLayout.HORIZONTAL_CENTER, loadButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, m, 0, SpringLayout.VERTICAL_CENTER, loadButton);

        back.add(loadButton);
        loadButton.add(m);
        loadButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if (loaded) {
                    loadButton.setIcon(new ImageIcon("gfx/buttonHover.pvz"));
                    mute();
                    new Thread(() -> new Game(new Levels(), muted)).start();
                    dispose();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(loaded) loadButton.setIcon(new ImageIcon("gfx/button.pvz"));
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        soundButton = new JLabel();
        soundButton.setIcon(new ImageIcon("gfx/buttons/sound.pvz"));
        soundButton.setBounds(745, 370, 100, 85);
        soundButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if (!muted) soundButton.setIcon(new ImageIcon("gfx/buttons/soundHover.pvz"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!muted) {
                    soundButton.setIcon(new ImageIcon("gfx/buttons/soundOff.pvz"));
                    mute();
                    muted = true;
                } else {
                    muted = false;
                    backPlay(MAIN_MENU);
                    soundButton.setIcon(new ImageIcon("gfx/buttons/sound.pvz"));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        back.add(soundButton);
        exitButton = new JLabel();
        exitButton.setIcon(new ImageIcon("gfx/buttons/exit.pvz"));
        exitButton.setBounds(745, 5, 100, 85);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                exitButton.setIcon(new ImageIcon("gfx/buttons/exitHover.pvz"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                exitButton.setIcon(new ImageIcon("gfx/buttons/exit.pvz"));
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        back.add(exitButton);
    }
}
