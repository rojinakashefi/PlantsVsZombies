package Menus;

import Main.Main;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * SavingMenu class performed the GUI and listeners of this section .
 * @author Kashefi
 * @since 2021
 */
public class SavingMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container This = this.getContentPane();
    JLabel headLabel;
    JButton newSave = new JButton("New Save");

    /**
     * Main constructor of the SavingMenu.class
     * @param game the game
     * @param pauseMenu the pause menue
     */
    public SavingMenu(Game game, PauseMenu pauseMenu) {
        headLabel = new JLabel("Saving Menu");
        newSave.addActionListener(e -> {
            Main.saves.add(game.save());
            Sounds.mute();
            new MainMenu(game.newLevel);
            game.dispose();
            pauseMenu.dispose();
            dispose();
        });
        this.setLayout(layout);
        this.setSize(340, 405);
        layout.putConstraint(SpringLayout.NORTH, headLabel, 20, SpringLayout.NORTH, This);
        layout.putConstraint(SpringLayout.WEST, headLabel, 20, SpringLayout.WEST, This);
        layout.putConstraint(SpringLayout.NORTH, newSave, 20, SpringLayout.NORTH, This);
        layout.putConstraint(SpringLayout.EAST, newSave, -20, SpringLayout.EAST, This);
        add(headLabel);
        add(newSave);

        if (Main.saves.size() > 0) showSaves(game, pauseMenu);
        else {
            JLabel emptyBox = new JLabel("No save files available!");
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, emptyBox, 0, SpringLayout.VERTICAL_CENTER, This);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emptyBox, 0, SpringLayout.HORIZONTAL_CENTER, This);
            add(emptyBox);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method sets the GUI of the saving menu.
     * @param menu main menu.
     */
    public SavingMenu(MainMenu menu) {
        headLabel = new JLabel("Loading Menu");
        this.setLayout(layout);
        this.setSize(340, 405);
        layout.putConstraint(SpringLayout.NORTH, headLabel, 20, SpringLayout.NORTH, This);
        layout.putConstraint(SpringLayout.WEST, headLabel, 20, SpringLayout.WEST, This);
        add(headLabel);
        if (Main.saves.size() > 0) {
            showSaves(menu);
        }
        else {
            JLabel emptyBox = new JLabel("No save files available!");
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, emptyBox, 0, SpringLayout.VERTICAL_CENTER, This);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emptyBox, 0, SpringLayout.HORIZONTAL_CENTER, This);
            add(emptyBox);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method sets the GUI of showing saves part of saving menu and sets the listener of the JPanel.
     * @param game the game.
     * @param pauseMenu the pause menu.
     */
    private void showSaves(Game game, PauseMenu pauseMenu) {
        JPanel panel = new JPanel(layout);
        layout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.SOUTH, headLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, This);
        panel.setPreferredSize(new Dimension(300, 300));
        JScrollPane s = new JScrollPane(panel);
        layout.putConstraint(SpringLayout.NORTH, s, 20, SpringLayout.SOUTH, headLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, s, 0, SpringLayout.HORIZONTAL_CENTER, This);
        this.add(s);
        for (int i = 0; i < Main.saves.size(); i++) {
            JPanel temp = addPanel(panel, i);
            int finalI = i;
            temp.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mousePressed(MouseEvent e) {

                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    Sounds.mute();
                    Main.saves.set(finalI, game.save());
                    new MainMenu(game.newLevel);
                    game.dispose();
                    pauseMenu.dispose();
                    dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            panel.repaint();
            repaint();
        }
    }

    /**
     * This method sets the GUI of showing saves and sets the mouse listener of this part.
     * @param menu the main menu
     */
    private void showSaves(MainMenu menu) {
        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(new Dimension(300, 300));
        layout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.SOUTH, headLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, This);
        JScrollPane s = new JScrollPane(panel);
        layout.putConstraint(SpringLayout.NORTH, s, 20, SpringLayout.SOUTH, headLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, s, 0, SpringLayout.HORIZONTAL_CENTER, This);
        this.add(s);
        for (int i = 0; i < Main.saves.size(); i++) {
            JPanel temp = addPanel(panel, i);
            int finalI = i;
            temp.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mousePressed(MouseEvent e) {

                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    Sounds.mute();
                    new Game(Main.saves.get(finalI), menu.player, Sounds.muted);
                    //menu.main.close();
                    menu.dispose();
                    dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    /**
     * This method performed the GUI of this section.
     * @param panel panel
     * @param i the index
     * @return the JPanel that we sets.
     */
    private JPanel addPanel(JPanel panel, int i) {
        JPanel temp = new JPanel(layout);
        temp.setPreferredSize(new Dimension(295, 95));

        panel.add(temp);
        panel.setName(String.valueOf(i));
        temp.setBackground(Color.gray);
        layout.putConstraint(SpringLayout.NORTH, temp, 3 + (95 * i), SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, temp, 0, SpringLayout.HORIZONTAL_CENTER, panel);

        JLabel name = new JLabel("Game Save " + (i + 1));
        layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, temp);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, name, 0, SpringLayout.VERTICAL_CENTER, temp);
        temp.add(name);

        JLabel time = new JLabel("Elapsed Time " + Main.saves.get(i).gameTime);
        layout.putConstraint(SpringLayout.WEST, time, 50, SpringLayout.EAST, name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, time, 0, SpringLayout.VERTICAL_CENTER, temp);
        temp.add(time);
        return temp;
    }
}
