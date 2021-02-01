package Menus;

import Miscs.Icons;
import Miscs.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingMenu extends JFrame {
    SpringLayout layout = new SpringLayout();
    Container pane = this.getContentPane();
    JCheckBox muteCheckBox = new JCheckBox("Sounds and Music");
    JLabel closeButton = new JLabel();
    JLabel modeHButton = new JLabel();
    JLabel modeNButton = new JLabel();
    JLabel nameLBL = new JLabel("Mode :");
    JLabel mode;
    MainMenu menu;
    public SettingMenu(MainMenu main) {
        menu = main;

        nameTXT = new JTextField(menu.username, 15);
        closeButton.setIcon(Icons.closeDialogIcon);
        modeHButton.setIcon(Icons.modeHIcon);
        modeNButton.setIcon(Icons.modeNOffIcon);
        pane.setLayout(layout);
        JLabel pauseFrame = new JLabel();
        pauseFrame.setIcon(Icons.settingsIcon);
        muteCheckBox.setSelected(!Game.mute);

        muteCheckBox.addChangeListener(e -> {
            Game.mute = !muteCheckBox.isSelected();
            if (!muteCheckBox.isSelected()) Sounds.mute();
            else {
                Sounds.muted = false;
                Sounds.backPlay(Sounds.IN_GAME);
            }
        });
        pane.add(muteCheckBox);
        pane.add(modeNButton);
        pane.add(closeButton);
        pane.add(modeHButton);
        pane.add(nameTXT);
        pane.add(nameLBL);
        pane.add(pauseFrame);
        layout.putConstraint(SpringLayout.WEST, muteCheckBox, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, muteCheckBox, 110, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, nameLBL, 35, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, nameLBL, 150, SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.WEST, nameTXT, 105, SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, nameTXT, 148, SpringLayout.NORTH, pane);

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
                menu.mode = 0;
                modeNButton.setIcon(Icons.modeNOffIcon);
                modeHButton.setIcon(Icons.modeHIcon);
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
                menu.mode = 1;
                modeNButton.setIcon(Icons.modeNIcon);
                modeHButton.setIcon(Icons.modeHOffIcon);
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
                if (nameTXT.getText().equals("")) nameTXT.setText(menu.username);
                menu.username = nameTXT.getText();
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
