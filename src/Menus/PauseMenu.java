package Menus;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PauseMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel quitButton = new JLabel();
    JLabel saveButton = new JLabel();
    Game mainGame;
    public PauseMenu(Game game){
        mainGame = game;
        quitButton.setIcon(Icons.quitGameIcon);
        closeButton.setIcon(Icons.closeDialogIcon);
        saveButton.setIcon(Icons.saveNExitIcon);
        pane.setLayout(layout);
        JLabel pauseFrame = new JLabel();
        pauseFrame.setIcon(Icons.pauseMenu);
        pane.add(pauseFrame);
        muteCheckBox.setSelected(!Game.mute);

        muteCheckBox.addChangeListener(e -> {
            Game.mute = !muteCheckBox.isSelected();
            if (!muteCheckBox.isSelected()) Sounds.mute();
            else {
                Sounds.muted = false;
                Sounds.backPlay(Sounds.IN_GAME);
            }
        });
        layout.putConstraint(SpringLayout.WEST, muteCheckBox, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, muteCheckBox, 110, SpringLayout.NORTH, pane);

        layout.putConstraint(SpringLayout.WEST, closeButton, 481, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, closeButton, 13, SpringLayout.NORTH, pane);

        layout.putConstraint(SpringLayout.WEST, quitButton, 96, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, quitButton, 252, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, saveButton, 284, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 254, SpringLayout.NORTH, pane);

        addClickListeners();

        setSize(533, 323);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }
            @Override
            public void windowClosing(WindowEvent e) {
                game.resume();
            }
            @Override
            public void windowClosed(WindowEvent e) {

            }
            @Override
            public void windowIconified(WindowEvent e) {

            }
            @Override
            public void windowDeiconified(WindowEvent e) {

            }
            @Override
            public void windowActivated(WindowEvent e) {

            }
            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
    private void addClickListeners() {
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mainGame.newLevel.gameSave(mainGame);
                dispose();
                mainGame.dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        closeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mainGame.resume();
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

