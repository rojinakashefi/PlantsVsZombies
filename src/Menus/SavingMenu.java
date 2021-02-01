package Menus;

import Main.Main;
import Miscs.GameSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SavingMenu extends JFrame{
    public SavingMenu(Game game, PauseMenu pauseMenu) {
        SpringLayout layout = new SpringLayout();
        Container This = this.getContentPane();
        this.setLayout(layout);
        this.setSize(340, 360);
        JLabel headLabel = new JLabel("Saving Menu");
        layout.putConstraint(SpringLayout.NORTH, headLabel, 20, SpringLayout.NORTH, This);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headLabel, 0, SpringLayout.HORIZONTAL_CENTER, This);
        add(headLabel);
        JPanel panel = new JPanel(layout);
        panel.setSize(300, 300);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
        for (int i = 0; i < Main.saves.size(); i++) {
            JPanel temp = new JPanel(layout);
            temp.setSize(295, 95);
            panel.add(temp);
            panel.setName(String.valueOf(i));
            layout.putConstraint(SpringLayout.NORTH, temp, 5 + (95 * i), SpringLayout.NORTH, panel);
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, temp, 0, SpringLayout.HORIZONTAL_CENTER, panel);

            JLabel name = new JLabel("Game Save " + (i + 1));
            layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, temp);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, name, 0, SpringLayout.VERTICAL_CENTER, temp);
            temp.add(name);

            JLabel time = new JLabel("Elapsed Time " + Main.saves.get(i).gameTime);
            layout.putConstraint(SpringLayout.WEST, time, 50, SpringLayout.WEST, name);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, time, 0, SpringLayout.VERTICAL_CENTER, temp);
            temp.add(time);
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
                    Main.saves.remove(finalI);
                    Main.saves.add(game.save());
                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        layout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.NORTH, headLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, This);
        panel.setBackground(Color.darkGray);
    }
}
