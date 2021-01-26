package Menus;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;

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
    }
}
