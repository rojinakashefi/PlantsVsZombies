package Menus;
import Main.Main;
import Miscs.Icons;
import Miscs.Sounds;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * SettingMenu is Gui of setting Menu
 * Contains of mute or unmuting game and Mode of game(Hard or normal)
 * @author RojinaKashefi && HeliaHashemipour
 */
public class SettingMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel modeHButton = new JLabel();
    JLabel modeNButton = new JLabel();
    JLabel modeLBL = new JLabel("Mode :");
    JLabel nameLBL = new JLabel("Username :");
    JTextField nameTXT = new JTextField(15);
    JLabel mode;
    MainMenu menu;
    /**
     * shows setting menu for the main menu
     * @param main gets the mainMenu object to modify its settings
     */
    public SettingMenu(MainMenu main) {
        menu = main;
        mode = new JLabel();
        //adding close button pics
        closeButton.setIcon(Icons.closeDialogIcon);
        //if difficulty is 0 in normalmode
        if (menu.player.difficulty == 0) {
            modeHButton.setIcon(Icons.modeHIcon);
            modeNButton.setIcon(Icons.modeNOffIcon);
            mode.setText("Normal");
        } else {
            //if difficulty is 1 in Hard mode
            modeNButton.setIcon(Icons.modeNIcon);
            modeHButton.setIcon(Icons.modeHOffIcon);
            mode.setText("Hard");
        }
        pane.setLayout(layout);
        nameTXT.setText(main.player.username);
        JLabel pauseFrame = new JLabel();
        pauseFrame.setIcon(Icons.settingsIcon);
        muteCheckBox.setSelected(!Sounds.muted);
        //adding check box a change listener for checking if game is mute or not
        muteCheckBox.addChangeListener(e -> {
            Sounds.muted = !muteCheckBox.isSelected();
            if (!muteCheckBox.isSelected()) {
                Sounds.mute();
                Sounds.muted = true;
            }
            else {
                Sounds.muted = false;
                Sounds.mute();
                Sounds.backPlay(Sounds.MAIN_MENU);
            }
        });
        pane.add(muteCheckBox);
        pane.add(modeNButton);
        pane.add(closeButton);
        pane.add(modeHButton);
        pane.add(mode);
        pane.add(modeLBL);
        pane.add(nameLBL);
        pane.add(nameTXT);
        pane.add(pauseFrame);
        layout.putConstraint(SpringLayout.WEST, muteCheckBox, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, muteCheckBox, 110, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, modeLBL, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, modeLBL, 150, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, mode, 105, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, mode, 148, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, nameLBL, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, nameLBL, 180, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, nameTXT, 105, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, nameTXT, 178, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, closeButton, 481, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, closeButton, 13, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, modeNButton, 83, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, modeNButton, 251, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, modeHButton, 283, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, modeHButton, 250, SpringLayout.NORTH, pane);
        addClickListeners();
        setSize(533, 323);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * add this clickListeners to ModeNormalButton
     * add this clickListeners to ModeHard button
     * add this clickListeners to close button
     */
    private void addClickListeners() {
        modeNButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                menu.player.difficulty = 0;
                modeNButton.setIcon(Icons.modeNOffIcon);
                modeHButton.setIcon(Icons.modeHIcon);
                mode.setText("Normal");
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        modeHButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                menu.player.difficulty = 1;
                modeNButton.setIcon(Icons.modeNIcon);
                modeHButton.setIcon(Icons.modeHOffIcon);
                mode.setText("Hard");
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
                if (!menu.player.username.equals(nameTXT.getText())) {
                    int index = Main.findPlayerIndex(menu.player.username);
                    if (Main.findPlayerIndex(nameTXT.getText()) == -1) {
                        menu.player.username = nameTXT.getText();
                        Main.loadedPlayers.set(index, menu.player);
                        dispose();
                    } else {
                        new JOptionPane("Username is already in use!", JOptionPane.INFORMATION_MESSAGE)
                                .createDialog("").setVisible(true);
                    }
                } else dispose();
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

