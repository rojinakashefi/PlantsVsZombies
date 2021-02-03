package Menus;
import Miscs.Icons;
import Miscs.Sounds;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pause Menu gui class
 * Contains of closeButton quitButton saveButton
 * @author RojinaKashefi && HeliaHashemipour
 */
public class PauseMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel quitButton = new JLabel();
    JLabel saveButton = new JLabel();
    Game mainGame;

    /**
     * shows the pauseMenu of the given game
     * @param game the parent container of the menu and game we want to set changes in
     */
    public PauseMenu(Game game) {
        mainGame = game;
        quitButton.setIcon(Icons.quitGameIcon);
        closeButton.setIcon(Icons.closeDialogIcon);
        saveButton.setIcon(Icons.saveNExitIcon);
        pane.setLayout(layout);
        JLabel pauseFrame = new JLabel();
        pauseFrame.setIcon(Icons.pauseMenu);
        pane.add(pauseFrame);
        muteCheckBox.setSelected(!Game.mute);
        //changeListener for checking if mute check box has changed or nor
        muteCheckBox.addChangeListener(e -> {
            if(muteCheckBox.isSelected() && Game.mute) {
                Game.mute = false;
                Sounds.muted = false;
                Sounds.backPlay(Sounds.IN_GAME);
            } else if (!muteCheckBox.isSelected() && !Game.mute) {
                Game.mute = true;
                Sounds.muted = true;
                Sounds.mute();
            }
        });
        pane.add(muteCheckBox);
        pane.add(saveButton);
        pane.add(closeButton);
        pane.add(quitButton);
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

    /**
     * Contains mouselistener of saveButton
     * it opens SavingMenu
     * Contains of CloseButton
     * it remuse game (x)
     * Contains Quit button
     * it quits game(open MainMen)
     */
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
                new SavingMenu(mainGame, PauseMenu.this);
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
                dispose();
                mainGame.dispose();
                new MainMenu(mainGame.newLevel);
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
